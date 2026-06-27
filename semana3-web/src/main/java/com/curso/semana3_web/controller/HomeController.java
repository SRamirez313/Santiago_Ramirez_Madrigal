package com.curso.semana3_web.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
 
@Controller
public class HomeController {
 
    @GetMapping("/")
    public String home(Model model) {
 
        model.addAttribute("nombre", "Clase SC-403");
 
        return "home";
    }
 
    @GetMapping("/cursos")
    public String cursos(Model model) {
 
        model.addAttribute(
            "cursos",
            java.util.List.of(
                "Java",
                "Spring Boot",
                "Thymeleaf",
                "Bootstrap 5",
                "MySQL"
            )
        );
 
        return "cursos";
    }
}