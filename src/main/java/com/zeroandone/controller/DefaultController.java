package com.zeroandone.controller;

import com.zeroandone.repository.PivotTemplateRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class DefaultController {

    private final PivotTemplateRepository pivotTemplateRepository;

    public DefaultController(PivotTemplateRepository pivotTemplateRepository) {
        this.pivotTemplateRepository = pivotTemplateRepository;
    }

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public ModelAndView login() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
    			/* The user is logged in :) */
            return new ModelAndView("redirect:/dashboard");
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
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

    @GetMapping({"/"})
    public ModelAndView partnerOrEmployee(HttpServletRequest request) {

        ModelAndView modelAndView;

        if (request.isUserInRole("ROLE_EMPLOYEE")) {
            modelAndView = new ModelAndView("member/dashboard");

        }
        else {
            modelAndView = new ModelAndView("redirect:/partner/statistics");
        }
        return modelAndView;
    }



    @GetMapping({"/dashboard"})
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
