package com.github.danrog303.poketch.captcha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Google reCAPTCHA implementation of {@link CaptchaService}.
 */
@Service
public class GoogleReCaptchaService implements CaptchaService {
    private final RestTemplate restTemplate;
    private final GoogleReCaptchaConfig captchaConfig;

    @Override
    public void validateCaptcha(String captchaResponse) throws InvalidCaptchaException {
        if(!responseSanityCheck(captchaResponse)) {
            throw new InvalidCaptchaException("Response contains invalid characters");
        }

        var endpoint = "/api/siteverify?secret={secret}&response={response}";
        var uriParams = new HashMap<String, String>();
        uriParams.put("secret", captchaConfig.getKeySecret());
        uriParams.put("response", captchaResponse);

        URI verifyUri = restTemplate.getUriTemplateHandler().expand(endpoint, uriParams);
        GoogleReCaptchaResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleReCaptchaResponse.class);

        if(googleResponse == null || !googleResponse.isSuccess()) {
            throw new InvalidCaptchaException("Captcha was not successfully validated");
        }
    }

    private boolean responseSanityCheck(String response) {
        Pattern responsePattern = Pattern.compile("[A-Za-z0-9_-]+");
        return StringUtils.hasLength(response) && responsePattern.matcher(response).matches();
    }

    @Autowired
    public GoogleReCaptchaService(@Qualifier("captchaRestTemplate") RestTemplate template, GoogleReCaptchaConfig config) {
        this.restTemplate = template;
        this.captchaConfig = config;
    }
}