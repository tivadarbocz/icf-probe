package com.icf.views.content;

import com.icf.views.badge.BadgeUtil;
import com.icf.views.badge.WrapperCard;
import com.icf.views.main.MainView;
import com.icf.views.secured.SecuredDiv;
import com.thedeanda.lorem.LoremIpsum;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@Route(value = "content", layout = MainView.class)
@PageTitle("Content management")
@Secured({"ROLE_ADMIN", "ROLE_CONTENT_EDITOR"})
public class ContentEditorView extends SecuredDiv {

    private final H2 h2RandomText1 = new H2();

    public ContentEditorView() {
        final WrapperCard wrapperCard = BadgeUtil.createBadge("Content editor view", this.h2RandomText1, "primary-text", LoremIpsum.getInstance().getWords(1, 5), "badge");
        this.h2RandomText1.setText(LoremIpsum.getInstance().getWords(0, 5));

        this.add(wrapperCard);
    }

}
