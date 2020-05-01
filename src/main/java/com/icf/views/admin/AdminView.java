package com.icf.views.admin;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;

@Route("admin")
@PageTitle("admin")
@Secured("ROLE_ADMIN")
public class AdminView {
}
