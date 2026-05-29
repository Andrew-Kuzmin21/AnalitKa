package com.kuzmin.Project_i.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер главной страницы.
 */
@Controller
public class HomeController {

    /**
     * Главная страница сайта
     */
    @GetMapping("/")
    public String homePage() {



        return "redirect:/dashboards/default";
    }
}
