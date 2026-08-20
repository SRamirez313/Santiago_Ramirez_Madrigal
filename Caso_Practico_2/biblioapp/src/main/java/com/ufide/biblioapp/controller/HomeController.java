package com.ufide.biblioapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/libros";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

        @GetMapping("/403")
    public String accesoDenegado() {
        return "403";
    }
}
