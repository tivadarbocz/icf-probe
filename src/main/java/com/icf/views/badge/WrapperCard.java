package com.icf.views.badge;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;

public class WrapperCard extends Div {

    public WrapperCard(String className, Component[] components,
                       String... classes) {
        this.addClassName(className);

        Div card = new Div();
        card.addClassNames(classes);
        card.add(components);

        this.add(card);
    }

}
