package com.github.danrog303.poketch.extras;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller responsible for handling extra features of Poketch.
 */
@Controller
@RequestMapping("/extras")
public class ExtrasController {
    @GetMapping("/box-utility-tool")
    public String showBoxUtilityTool() {
        return "pages/box-utility";
    }
}
