package com.peergreen.webconsole.scope.home.extensions;

import com.peergreen.webconsole.Extension;
import com.peergreen.webconsole.ExtensionPoint;
import com.vaadin.ui.TextArea;

/**
 * @author Mohammed Boukada
 */
@Extension
@ExtensionPoint("com.peergreen.webconsole.scope.home.HomeScope.top.right")
public class NotesFrame extends TextArea {

    public NotesFrame() {
        setValue("Friday may 17, 2013 : OSGi France user group conf'");
        //setCaption("Notes");
        setSizeFull();
        addStyleName("borderless");
    }
}
