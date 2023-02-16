package com.github.danrog303.poketch.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Service responsible for instantiating email templates
 * and sending them to the specified email address.
 */
@Service
public class AppEmailService {
    private final EmailSender emailSender;
    private final TemplateEngine emailTemplateEngine;

    /**
     * Sends email confirmation message to the specified email address.
     */
    public void sendRegistrationTokenEmail(String toEmailAddress, String confirmationLink) {
        Context emailCtx = new Context();
        emailCtx.setVariable("confirmationLink", confirmationLink);

        String emailBody = emailTemplateEngine.process("mails/email-confirmation", emailCtx);
        String emailSubject = "Poketch: Confirm your email";
        emailSender.send(toEmailAddress, emailSubject, emailBody);
    }

    @Autowired
    public AppEmailService(EmailSender emailSender, @Qualifier("emailTemplateEngine") TemplateEngine emailTemplateEngine) {
        this.emailSender = emailSender;
        this.emailTemplateEngine = emailTemplateEngine;
    }
}
