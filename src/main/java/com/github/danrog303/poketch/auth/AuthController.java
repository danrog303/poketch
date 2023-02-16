package com.github.danrog303.poketch.auth;

import com.github.danrog303.poketch.captcha.CaptchaService;
import com.github.danrog303.poketch.captcha.InvalidCaptchaException;
import com.github.danrog303.poketch.registration.RegistrationException;
import com.github.danrog303.poketch.registration.RegistrationRequest;
import com.github.danrog303.poketch.registration.RegistrationService;
import com.github.danrog303.poketch.user.credentials.EmailAlreadyExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

/**
 * Controller responsible for handling login and registration requests.
 */
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegistrationService registrationService;
    private final CaptchaService captchaService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "pages/login-form";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "pages/registration-form";
    }

    @PostMapping("/register")
    public String handleRegistrationForm(@Valid @ModelAttribute RegistrationRequest registrationRequest,
                                         BindingResult validationResult,
                                         @RequestParam(name="g-recaptcha-response") String captcha) {
        if (validationResult.hasErrors()) {
            return "pages/registration-form";
        } else {
            captchaService.validateCaptcha(captcha);
            registrationService.register(registrationRequest);
            return "redirect:/auth/login?registrationSuccess";
        }
    }

    @ExceptionHandler
    public String handleCaptchaExceptions(InvalidCaptchaException e) {
        return "redirect:/auth/register?captchaError";
    }

    @ExceptionHandler
    public String handleEmailAlreadyExistsExceptions(EmailAlreadyExistsException e, Model model) {
        return "redirect:/auth/register?emailExists";
    }

    @GetMapping("/register/confirm")
    public String confirmUserEmail(@RequestParam String token, Model model) {
        registrationService.confirmToken(token);
        model.addAttribute("message", "Token has been confirmed");
        return "pages/message-show";
    }

    @ExceptionHandler
    public String handleRegistrationExceptions(RegistrationException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "pages/message-show";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
