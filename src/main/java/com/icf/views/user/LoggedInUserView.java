package com.icf.views.user;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@Route("content")
@PageTitle("Logged in user")
@Secured({"ROLE_ADMIN", "ROLE_CONTENT_EDITOR", "ROLE_LOGGED_IN_USE"})
public class LoggedInUserView extends VerticalLayout {
}
