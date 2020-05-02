package com.icf.views.main;

import com.icf.backend.enumeration.AvailableRole;
import com.icf.backend.model.Role;
import com.icf.backend.model.UserPrincipal;
import com.icf.backend.util.SecurityUtil;
import com.icf.views.admin.AdminView;
import com.icf.views.content.ContentEditorView;
import com.icf.views.dashboard.DashboardView;
import com.icf.views.user.LoggedInUserView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The main view is a top-level placeholder for other views.
 */
@JsModule("./styles/shared-styles.js")
@PWA(name = "Probe", shortName = "Probe")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainView extends AppLayout {

    private final Tabs menu;
    private final UserPrincipal loggedUser;

    public MainView() {
        this.loggedUser = SecurityUtil.getLoggedUser();
        this.setPrimarySection(Section.DRAWER);
        this.initNavBar();
        this.menu = this.createMenuTabs();
        this.addToDrawer(this.createUserInfoLayout());
        this.addToDrawer(this.menu);
    }

    private void initNavBar() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        this.addToNavbar(true,
                new DrawerToggle(),
                new H6("Last logged in: "),
                new H6(this.loggedUser.getUser().getLastLoggedInAt().format(formatter)),
                new H6(" | "),
                new H6("Role(s): "),
                new H6(this.loggedUser.getUser().getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.joining(", ")))
        );
    }

    private Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();

        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(this.getAvailableTabs());

        return tabs;
    }

    private VerticalLayout createUserInfoLayout() {
        final VerticalLayout vlUserInfo = new VerticalLayout();
        final Icon iconUser = new Icon(VaadinIcon.USER);

        vlUserInfo.add(iconUser);
        vlUserInfo.add(new H6(this.loggedUser.getUsername()));

        return vlUserInfo;
    }

    private Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        final Anchor logout = new Anchor("/logout", "Logout");

        tabs.add(this.createTab("Dashboard", DashboardView.class));

        if (SecurityUtil.givenUserHasRole(this.loggedUser, AvailableRole.ROLE_ADMIN.name())) {
            tabs.add(this.createTab("Admin", AdminView.class));
        }

        if (SecurityUtil.givenUserHasRole(this.loggedUser, AvailableRole.ROLE_CONTENT_EDITOR.name()) || SecurityUtil.givenUserHasRole(this.loggedUser, AvailableRole.ROLE_ADMIN.name())) {
            tabs.add(this.createTab("Content", ContentEditorView.class));
        }

        if (SecurityUtil.givenUserHasRole(this.loggedUser, AvailableRole.ROLE_LOGGED_IN_USER.name()) || SecurityUtil.givenUserHasRole(this.loggedUser, AvailableRole.ROLE_ADMIN.name())) {
            tabs.add(this.createTab("Logged in user  ", LoggedInUserView.class));
        }

        tabs.add(this.createTab(logout));

        return tabs.toArray(new Tab[tabs.size()]);
    }

    private Tab createTab(String title, Class<? extends Component> viewClass) {
        return this.createTab(this.populateLink(new RouterLink(null, viewClass), title));
    }

    private Tab createTab(Component content) {
        final Tab tab = new Tab();
        tab.add(content);
        return tab;
    }

    private <T extends HasComponents> T populateLink(T a, String title) {
        a.add(title);
        return a;
    }

    private void selectTab() {
        String target = RouteConfiguration.forSessionScope().getUrl(this.getContent().getClass());
        Optional<Component> tabToSelect = this.menu.getChildren().filter(tab -> {
            Component child = tab.getChildren().findFirst().get();
            return child instanceof RouterLink && ((RouterLink) child).getHref().equals(target);
        }).findFirst();
        tabToSelect.ifPresent(tab -> this.menu.setSelectedTab((Tab) tab));
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        this.selectTab();
    }

}
