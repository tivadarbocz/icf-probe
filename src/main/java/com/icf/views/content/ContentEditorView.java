package com.icf.views.content;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@Route("content")
@PageTitle("Content management")
@Secured({"ROLE_ADMIN", "ROLE_CONTENT_EDITOR"})
public class ContentEditorView extends VerticalLayout {

    public ContentEditorView() {
    }

}
