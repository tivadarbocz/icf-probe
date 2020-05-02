package com.icf.views.login;

import com.icf.backend.config.AppConfig;
import com.icf.backend.service.LoginAttemptService;
import com.icf.backend.util.SecurityUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Route("login")
@PageTitle("Vaadin | Spring Boot")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AppConfig appConfig;

    private ReCaptcha reCaptcha;
    private Button btnValidate;
    private LoginForm loginForm = new LoginForm();

    public LoginView() {
        this.addClassName("login-view");
        this.setSizeFull();
        this.setAlignItems(Alignment.CENTER);
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        this.loginForm.setAction("login");
        this.add(new H1("Vaadin | Spring Boot"), this.loginForm);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        this.initAndAddCaptcha();
        this.initAndAddValidateButton();
        this.setCaptchaComponentsVisibility();
        this.setLoginFormVisibility();

        // inform the user about an authentication error
        if (!event.getLocation()
                .getQueryParameters()
                .getParameters()
                .getOrDefault("error", Collections.emptyList())
                .isEmpty()) {
            this.loginForm.setError(true);
        }
    }

    private void initAndAddCaptcha() {
        this.reCaptcha = new ReCaptcha(
                this.appConfig.getRecaptchaWebsiteKey(),
                this.appConfig.getRecaptchaSecretKey()
        );

        this.add(this.reCaptcha);
    }

    private void initAndAddValidateButton() {
        this.btnValidate = new Button("Validate", event -> {
            boolean valid = this.reCaptcha.isValid();

            if (this.reCaptcha.isValid()) {
                this.loginAttemptService.loginSucceeded(SecurityUtil.getClientIP(this.request));
                this.reCaptcha.setVisible(false);
                this.btnValidate.setVisible(false);
                this.loginForm.setVisible(true);
            } else {
                Notification notification = new Notification("Not valid");
                notification.setDuration(2000);
                notification.setPosition(Notification.Position.MIDDLE);
                notification.addThemeVariants(valid ? NotificationVariant.LUMO_SUCCESS : NotificationVariant.LUMO_ERROR);
                notification.open();
            }

        });

        this.add(this.btnValidate);
    }

    private void setCaptchaComponentsVisibility() {
        this.setCaptchaVisibility();
        this.setValidateButtonVisibility();
    }

    private void setCaptchaVisibility() {
        final String ip = SecurityUtil.getClientIP(this.request);
        this.reCaptcha.setVisible(this.loginAttemptService.isBlocked(ip));
    }

    private void setValidateButtonVisibility() {
        this.btnValidate.setVisible(this.reCaptcha.isVisible());
    }

    private void setLoginFormVisibility() {
        this.loginForm.setVisible(!this.reCaptcha.isVisible());
    }

}