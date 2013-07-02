package com.peergreen.webconsole.scope.home;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Mohammed Boukada
 */
public class FrameView extends VerticalLayout {

    private Button next;
    private Button previous;
    private CssLayout panel;
    private Label caption;
    private List<Component> components = new CopyOnWriteArrayList<>();
    private Map<Component, String> captions = new ConcurrentHashMap<>();
    private int state = 0;

    public FrameView() {
        setSizeFull();
        setStyleName("frame-panel");
        setSpacing(false);

        addComponent(createNavRow());
        addComponent(createPanel());
        setExpandRatio(panel, 1.5f);
    }

    public void setContent(Component content) {
        panel.removeAllComponents();
        content.setSizeFull();
        panel.addComponent(content);
        caption.setValue(captions.get(content));
    }

    private CssLayout createPanel() {
        panel = new CssLayout();
        panel.addStyleName("layout");
        panel.setSizeFull();
        return panel;
    }

    private HorizontalLayout createNavRow() {
        HorizontalLayout row = new HorizontalLayout();
        row.setSizeUndefined();
        row.setWidth("100%");
        row.setSpacing(true);
        caption = new Label();
        caption.addStyleName("h4");
        row.addComponent(caption);
        row.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);
        HorizontalLayout buttons = new HorizontalLayout();
        previous = new Button("<");
        previous.addClickListener(new PreviousButtonClickListener());
        previous.setVisible(false);
        buttons.addComponent(previous);
        next = new Button(">");
        next.addClickListener(new NextButtonClickListener());
        next.setVisible(false);
        buttons.addComponent(next);
        row.addComponent(buttons);
        row.setComponentAlignment(buttons, Alignment.MIDDLE_RIGHT);
        return row;
    }

    public void addFrame(Component component, String caption) {
        if (caption != null) captions.put(component, caption);
        if (components.size() == 0) setContent(component);
        else {
            next.setVisible(true);
            previous.setVisible(true);
        }
        components.add(component);
    }

    public void removeFrame(Component component) {
        if (components.contains(component)) components.remove(component);
        if (captions.containsKey(component)) captions.remove(component);
        if (components.size() <= 1) {
            next.setVisible(false);
            previous.setVisible(false);
        }
    }

    public class NextButtonClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            if (components.size() > 0) {
                state++;
                if (state == components.size()) state = 0;
                setContent(components.get(state));
            }
        }
    }

    public class PreviousButtonClickListener implements Button.ClickListener {

        @Override
        public void buttonClick(Button.ClickEvent event) {
            if (components.size() > 0) {
                state--;
                if (state < 0) state = components.size() - 1;
                setContent(components.get(state));
            }
        }
    }
}