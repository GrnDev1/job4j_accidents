package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    @GetMapping("/create")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "accidents/create";
    }

    @PostMapping("/create")
    public String save(@ModelAttribute Accident accident, @RequestParam List<Integer> ruleIds) {
        accident.setRules(ruleService.findAllById(ruleIds));
        accidentService.save(accident);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam int id) {
        Optional<Accident> accidentOptional = accidentService.findById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Accident with this id is not found");
            return "errors/404";
        }
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        model.addAttribute("accident", accidentOptional.get());
        return "accidents/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Accident accident, Model model, @RequestParam List<Integer> ruleIds) {
        accident.setRules(ruleService.findAllById(ruleIds));
        var isUpdated = accidentService.update(accident);
        if (!isUpdated) {
            model.addAttribute("message", "Accident with this id is not found");
            return "errors/404";
        }
        return "redirect:/";
    }
}