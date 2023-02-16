package com.github.danrog303.poketch.captcha;

/**
 * Service for captcha validation.
 */
public interface CaptchaService {
    /**
     * Validates user's captcha response.
     * Simply returns, when captcha was correct.
     * Throws {@link InvalidCaptchaException}, if captcha was invalid.
     */
    public void validateCaptcha(String captchaResponse) throws InvalidCaptchaException;
}
