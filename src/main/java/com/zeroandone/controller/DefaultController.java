package com.zeroandone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DefaultController {

    @GetMapping("/reports/statistics")
    public ModelAndView reports() {
        return new ModelAndView("member/reports/statistics");
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

}
