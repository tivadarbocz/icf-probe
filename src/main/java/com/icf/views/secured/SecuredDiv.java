package com.icf.views.secured;

import com.icf.backend.util.SecurityUtil;
import com.icf.views.login.LoginView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.NotFoundException;

/**
 * A regular Div with a secured flavour. If the SecurityUtil (in the beforeEnter method) doesn't find the mandatory role for the given view, then it prevents the access.
 * If the user doesn't have a role at all, then reroutes to the LoginView
 */
public class SecuredDiv extends Div implements BeforeEnterObserver {

    @Override
    public void beforeEnter(final BeforeEnterEvent beforeEnterEvent) {
        if (!SecurityUtil.isAccessGranted(beforeEnterEvent.getNavigationTarget())) {
            if (SecurityUtil.isUserLoggedIn()) {
                beforeEnterEvent.rerouteToError(NotFoundException.class);
            } else {
                beforeEnterEvent.rerouteTo(LoginView.class);
            }
        }
    }

}
