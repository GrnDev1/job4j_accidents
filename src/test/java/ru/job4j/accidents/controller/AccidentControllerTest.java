package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
class AccidentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccidentService accidentService;

    @Test
    @WithMockUser
    public void whenGetRequestCreateAccident() throws Exception {
        this.mockMvc.perform(get("/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("rules"))
                .andExpect(view().name("accidents/create"));
    }

    @Test
    @WithMockUser
    public void whenGetRequestEditAccident() throws Exception {
        var accident = new Accident(1, "n", "t", "a",
                new AccidentType(1, "Two cars"), Set.of(new Rule(1, "Rule 1")));
        when(accidentService.findById(accident.getId())).thenReturn(Optional.of(accident));
        this.mockMvc.perform(get("/edit").param("id", String.valueOf(accident.getId())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("rules"))
                .andExpect(model().attributeExists("accident"))
                .andExpect(view().name("accidents/edit"));
    }

    @Test
    @WithMockUser
    public void whenGetRequestEditAccidentThenError() throws Exception {
        this.mockMvc.perform(get("/edit").param("id", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("message"))
                .andExpect(view().name("errors/404"));
    }

    @Test
    @WithMockUser
    public void whenPostRequestSaveAccident() throws Exception {
        this.mockMvc.perform(post("/create")
                        .param("ruleIds", "1")
                        .param("ruleIds", "2"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).save(argument.capture());
        assertThat(argument.getValue().getRules().size()).isEqualTo(2);
    }

    @Test
    @WithMockUser
    public void whenPostRequestUpdateAccidentThenSuccess() throws Exception {
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        when(accidentService.update(argument.capture())).thenReturn(true);
        this.mockMvc.perform(post("/update")
                        .param("ruleIds", "1")
                        .param("ruleIds", "2"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(accidentService).update(argument.capture());
        assertThat(argument.getValue().getRules().size()).isEqualTo(2);
    }

    @Test
    @WithMockUser
    public void whenPostRequestUpdateAccidentThenError() throws Exception {
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        when(accidentService.update(argument.capture())).thenReturn(false);
        this.mockMvc.perform(post("/update")
                        .param("ruleIds", "1")
                        .param("ruleIds", "2"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("errors/404"))
                .andExpect(model().attribute("message", "Accident with this id is not found"));
        verify(accidentService).update(argument.capture());
        assertThat(argument.getValue().getRules().size()).isEqualTo(2);
    }
}