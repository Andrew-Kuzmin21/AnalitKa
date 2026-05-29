package com.kuzmin.Project_i.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage() {

        return "redirect:/dashboards";
    }
}