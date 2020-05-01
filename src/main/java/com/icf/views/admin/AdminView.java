package com.icf.views.admin;

import com.icf.backend.util.SecurityUtil;
import com.icf.views.badge.BadgeUtil;
import com.icf.views.badge.WrapperCard;
import com.icf.views.login.LoginView;
import com.icf.views.main.MainView;
import com.thedeanda.lorem.LoremIpsum;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@Route(value = "admin", layout = MainView.class)
@PageTitle("admin")
@Secured("ROLE_ADMIN")
@CssImport(value = "styles/views/dashboard/dashboard-view.css", include = "lumo-badge")
public class AdminView extends Div implements BeforeEnterObserver {

    private final H2 h2RandomText1 = new H2();

    public AdminView() {
        final WrapperCard wrapperCard = BadgeUtil.createBadge("Admin view", h2RandomText1, "primary-text", LoremIpsum.getInstance().getWords(1, 5), "badge");
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
