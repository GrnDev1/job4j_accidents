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
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.UserService;

import javax.transaction.Transactional;
import java.util.Optional;

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
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    public void whenGetRequestLoginPage() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users/login"));
    }

    @Test
    @WithMockUser
    public void whenGetRequestLoginPageWithError() throws Exception {
        this.mockMvc.perform(get("/login").param("error", "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(view().name("users/login"));
    }

    @Test
    @WithMockUser
    public void whenGetRequestLogoutPage() throws Exception {
        this.mockMvc.perform(get("/logout"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/login?logout=true"));
    }

    @Test
    @WithMockUser
    public void whenGetRequestRegistrationPage() throws Exception {
        this.mockMvc.perform(get("/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users/reg"));
    }

    @Test
    @WithMockUser
    public void whenPostRequestRegUserThenSuccess() throws Exception {
        User user = new User();
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        when(userService.save(argument.capture())).thenReturn(Optional.of(user));
        this.mockMvc.perform(post("/reg"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        verify(userService).save(argument.capture());
        assertThat(argument.getValue()).isEqualTo(user);
    }

    @Test
    @WithMockUser
    public void whenPostRequestRegUserThenError() throws Exception {
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        when(userService.save(argument.capture())).thenReturn(Optional.empty());
        this.mockMvc.perform(post("/reg"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("users/reg"))
                .andExpect(model().attribute("error", "User with this mail already exists"));
        verify(userService).save(argument.capture());
    }
}