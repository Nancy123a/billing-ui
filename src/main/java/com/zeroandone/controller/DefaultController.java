package com.zeroandone.controller;

import com.zeroandone.repository.PivotTemplateRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DefaultController {

    private final PivotTemplateRepository pivotTemplateRepository;

    public DefaultController(PivotTemplateRepository pivotTemplateRepository) {
        this.pivotTemplateRepository = pivotTemplateRepository;
    }

    @GetMapping("/reports/statistics")
    public ModelAndView reports() {
        return new ModelAndView("member/reports/statistics");
    }

    @GetMapping("/reports/custom")
    public ModelAndView custom() {
        ModelAndView modelAndView = new ModelAndView("member/reports/custom");
        modelAndView.addObject("templates",pivotTemplateRepository.findAll());
        return modelAndView;
    }


    @GetMapping({"/","/dashboard"})
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("member/dashboard");
        return modelAndView;
    }

    @GetMapping("/admin/country")
    public ModelAndView country() {
        return new ModelAndView("member/admin/country");
    }

    @GetMapping("/admin/operator")
    public ModelAndView operator() {
        return new ModelAndView("member/admin/operator");
    }

    @GetMapping("/admin/countrycode")
    public ModelAndView countrycode() {
        return new ModelAndView("member/admin/countrycode");
    }

    @GetMapping("/admin/operatorcode")
    public ModelAndView operatorcode() {
        return new ModelAndView("member/admin/operatorcode");
    }
}
