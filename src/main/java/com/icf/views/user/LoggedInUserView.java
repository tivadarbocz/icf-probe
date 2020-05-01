package com.icf.views.user;

import com.icf.backend.util.SecurityUtil;
import com.icf.views.badge.BadgeUtil;
import com.icf.views.badge.WrapperCard;
import com.icf.views.login.LoginView;
import com.icf.views.main.MainView;
import com.thedeanda.lorem.LoremIpsum;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@Route(value = "user", layout = MainView.class)
@PageTitle("user management")
@Secured({"ROLE_ADMIN", "ROLE_LOGGED_IN_USER"})
public class LoggedInUserView extends Div implements BeforeEnterObserver {

    private final H2 h2RandomText1 = new H2();

    public LoggedInUserView() {
        final WrapperCard wrapperCard = BadgeUtil.createBadge("User view", h2RandomText1, "primary-text", LoremIpsum.getInstance().getWords(1, 5), "badge");
        h2RandomText1.setText(LoremIpsum.getInstance().getWords(0, 5));

        this.add(wrapperCard);
    }

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
