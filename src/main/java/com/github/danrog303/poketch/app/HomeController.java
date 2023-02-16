package com.github.danrog303.poketch.app;

import com.github.danrog303.poketch.email.AppEmailService;
import lombok.Setter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping("/")
    public String getHomePage(Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/pokemon/list";
        } else {
            return "pages/home";
        }
    }
}
