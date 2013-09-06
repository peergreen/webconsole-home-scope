package com.peergreen.webconsole.scope.home.extensions;

import com.peergreen.newsfeed.FeedMessage;
import com.peergreen.newsfeed.Rss;
import com.peergreen.newsfeed.RssService;
import com.peergreen.newsfeed.RssServiceException;
import com.peergreen.webconsole.Extension;
import com.peergreen.webconsole.ExtensionPoint;
import com.peergreen.webconsole.Inject;
import com.peergreen.webconsole.scope.home.Frame;
import com.peergreen.webconsole.vaadin.DefaultWindow;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Mohammed Boukada
 */
@Extension
@ExtensionPoint("com.peergreen.webconsole.scope.home.HomeScope.top.left")
@Frame("Peergreen News")
public class PeergreenNewsFeedFrame extends Table {

    @Inject
    private RssService rssService;
    private final static String PEERGREEN_RSS_FLOW_URL = "http://www.peergreen.com/Blog/BlogRss?xpage=plain";

    public PeergreenNewsFeedFrame() {
        addContainerProperty("<p style=\"display:none\">Title</p>", Button.class, null);
        setWidth("100%");
        setPageLength(10);
        setImmediate(true);
        addStyleName("plain");
        addStyleName("borderless");
        setSortEnabled(false);
        setImmediate(true);
    }

    @PostConstruct
    public void init() throws MalformedURLException, RssServiceException {
        Rss rss = null;
        rss = rssService.parse(new URL(PEERGREEN_RSS_FLOW_URL));
        int i = 0;
        for(final FeedMessage feedMessage : rss.getItems()) {
            Button news = new NativeButton(feedMessage.getTitle());
            news.addStyleName("link");
            news.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    Window w = getNewsDescription(feedMessage);
                    UI.getCurrent().addWindow(w);
                    w.focus();
                }
            });

            addItem(new Object[]{news}, i++);
        }
    }

    /**
     * News popup
     * @param feedMessage
     * @return
     */
    private Window getNewsDescription(FeedMessage feedMessage) {
        FormLayout fields = new FormLayout();
        fields.setWidth("35em");
        fields.setSpacing(true);
        fields.setMargin(true);

        Label label = new Label("<a href=\"" + feedMessage.getLink() + "\">" + feedMessage.getLink().substring(0, 50) + "..." + "</a>");
        label.setContentMode(ContentMode.HTML);
        label.setSizeUndefined();
        label.setCaption("URL");
        fields.addComponent(label);

        String description = feedMessage.getDescription();
        if (description.length() > 1000) {
            description = description.substring(0, 999) + "...";
        }

        Label desc = new Label(description);
        desc.setContentMode(ContentMode.HTML);
        desc.setCaption("Description");
        fields.addComponent(desc);

        Button ok = new Button("Close");
        ok.addStyleName("wide");
        ok.addStyleName("default");

        final Window w = new DefaultWindow(feedMessage.getTitle(), fields, ok);
        w.center();
        ok.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                w.close();
            }
        });
        return w;
    }
}