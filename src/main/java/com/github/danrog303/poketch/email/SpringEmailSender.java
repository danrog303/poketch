package com.github.danrog303.poketch.email;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service that sends emails using {@link JavaMailSender}.
 */
@Service
@RequiredArgsConstructor
public class SpringEmailSender implements EmailSender {
    private final static Logger LOGGER = LoggerFactory.getLogger(SpringEmailSender.class);
    private final JavaMailSender mailSender;
    private final SpringEmailConfig springEmailConfig;

    @Async
    @Override
    public void send(String to, String subject, String messageHtml) {
        try {
            var mimeMessage = mailSender.createMimeMessage();
            var mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
            mimeMessageHelper.setText(messageHtml, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(springEmailConfig.getSenderAddress());
            mailSender.send(mimeMessage);
        } catch(MessagingException e) {
            LOGGER.error("Failed to send email", e);
            throw new IllegalStateException("Failed to send email", e);
        }
    }
}
