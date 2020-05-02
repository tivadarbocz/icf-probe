package com.icf.backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class AppConfig {

    @Value("${google.recaptcha.websiteKey}")
    private String recaptchaWebsiteKey;

    @Value("${google.recaptcha.secretKey}")
    private String recaptchaSecretKey;

}
