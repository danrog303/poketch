package com.github.danrog303.poketch.captcha;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * API configuration info for Google reCAPTCHA.
 */
@Configuration
@ConfigurationProperties(prefix="google.recaptcha")
public class GoogleReCaptchaConfig {
    private @Getter @Setter String keySite;
    private @Getter @Setter String keySecret;
    private @Getter @Setter String endpointRoot;

    @Bean
    public RestTemplate captchaRestTemplate() {
        return new RestTemplateBuilder().rootUri(endpointRoot).build();
    }
}
