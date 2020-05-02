package com.icf.views.admin;

import com.icf.views.badge.BadgeUtil;
import com.icf.views.badge.WrapperCard;
import com.icf.views.main.MainView;
import com.icf.views.secured.SecuredDiv;
import com.thedeanda.lorem.LoremIpsum;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@Route(value = "admin", layout = MainView.class)
@PageTitle("Admin")
@Secured("ROLE_ADMIN")
public class AdminView extends SecuredDiv {

    private final H2 h2RandomText1 = new H2();

    public AdminView() {
        final WrapperCard wrapperCard = BadgeUtil.createBadge("Admin view", this.h2RandomText1, "primary-text", LoremIpsum.getInstance().getWords(1, 5), "badge");
        this.h2RandomText1.setText(LoremIpsum.getInstance().getWords(0, 5));

        this.add(wrapperCard);
    }

}
