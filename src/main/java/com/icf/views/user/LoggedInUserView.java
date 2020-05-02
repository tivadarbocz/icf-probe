package com.icf.views.user;

import com.icf.views.badge.BadgeUtil;
import com.icf.views.badge.WrapperCard;
import com.icf.views.main.MainView;
import com.icf.views.secured.SecuredDiv;
import com.thedeanda.lorem.LoremIpsum;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@Route(value = "user", layout = MainView.class)
@PageTitle("User management")
@Secured({"ROLE_ADMIN", "ROLE_LOGGED_IN_USER"})
public class LoggedInUserView extends SecuredDiv {

    private final H2 h2RandomText1 = new H2();

    public LoggedInUserView() {
        final WrapperCard wrapperCard = BadgeUtil.createBadge("User view", this.h2RandomText1, "primary-text", LoremIpsum.getInstance().getWords(1, 5), "badge");
        this.h2RandomText1.setText(LoremIpsum.getInstance().getWords(0, 5));

        this.add(wrapperCard);
    }

}
