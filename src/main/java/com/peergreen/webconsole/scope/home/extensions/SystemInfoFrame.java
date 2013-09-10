package com.peergreen.webconsole.scope.home.extensions;

import static java.lang.Runtime.getRuntime;
import static java.lang.String.format;
import static java.lang.System.getProperty;

import javax.annotation.PostConstruct;

import com.peergreen.webconsole.Extension;
import com.peergreen.webconsole.ExtensionPoint;
import com.peergreen.webconsole.scope.home.Frame;
import com.vaadin.ui.Table;

/**
 * @author Mohammed Boukada
 */
@Extension
@ExtensionPoint("com.peergreen.webconsole.scope.home.HomeScope.top.right")
@Frame("Host properties")
public class SystemInfoFrame extends Table {

    private static final int MB = 1024 * 1024;

    public SystemInfoFrame() {
        addContainerProperty("Name", String.class, null);
        addContainerProperty("Value", String.class, null);
        setWidth("100%");
        setPageLength(10);
        setImmediate(true);
        addStyleName("plain");
        addStyleName("borderless");
        setSortEnabled(false);
        setImmediate(true);
    }

    @PostConstruct
    public void init() {
        showProperties();
    }

    @Override
    public void attach() {
        super.attach();
        showProperties();
    }

    private void showProperties() {
        removeAllItems();
        addItem(new Object[]{"Operating System", format("%s - version %s", getProperty("os.name"), getProperty("os.version"))}, 0);
        addItem(new Object[]{"Virtual Machine", format("%s - version %s", getProperty("java.vm.name"), getProperty("java.vm.version"))}, 1);
        addItem(new Object[]{"JVM used memory", format("%d / %d MB", (getRuntime().totalMemory() - getRuntime().freeMemory()) / MB, getRuntime().totalMemory() / MB)}, 2);
        addItem(new Object[]{"JVM max memory", format("%d MB", getRuntime().maxMemory() / MB)}, 3);
        addItem(new Object[]{"JVM Available processors", format("%d core(s)", getRuntime().availableProcessors() / MB)}, 4);
    }
}
