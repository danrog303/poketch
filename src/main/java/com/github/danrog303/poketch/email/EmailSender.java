package com.github.danrog303.poketch.email;

/**
 * Interface for email sending services.
 */
public interface EmailSender {
    void send(String to, String subject, String messageHtml);
}
