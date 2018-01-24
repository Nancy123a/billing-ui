package com.zeroandone.controller;

import com.zeroandone.repository.UserOperatorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PartnerController {

    @GetMapping("/partner/statistics")
    public ModelAndView reports() {
        return new ModelAndView("member/partner/statistics");
    }
}
