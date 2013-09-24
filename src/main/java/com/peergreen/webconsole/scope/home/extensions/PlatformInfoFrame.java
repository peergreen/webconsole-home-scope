package com.peergreen.webconsole.scope.home.extensions;

import static java.lang.String.format;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import com.peergreen.kernel.info.PlatformInfo;
import com.peergreen.webconsole.Extension;
import com.peergreen.webconsole.ExtensionPoint;
import com.peergreen.webconsole.Inject;
import com.peergreen.webconsole.scope.home.Frame;
import com.vaadin.ui.Table;

/**
 * @author Mohammed Boukada
 */
@Extension
@ExtensionPoint("com.peergreen.webconsole.scope.home.HomeScope.bottom.left")
@Frame("Platform information")
public class PlatformInfoFrame extends Table {

    @Inject
    private PlatformInfo platformInfo;

    public PlatformInfoFrame() {
        addContainerProperty("Name", String.class, null);
        addContainerProperty("Value", String.class, null);
        setWidth("100%");
        setPageLength(10);
        addStyleName("plain");
        addStyleName("borderless");
        setSortEnabled(false);
        setImmediate(true);
    }

    @PostConstruct
    public void init() {
        showInfos();
    }

    @Override
    public void attach() {
        super.attach();
        showInfos();
    }

    private void showInfos() {
        removeAllItems();
        addItem(new Object[]{"Platform Id", format("%s", platformInfo.getId())}, 0);
        addItem(new Object[]{"Started", format("%tc%n", platformInfo.getStartDate())}, 1);
        addItem(new Object[]{"Boot time", format("%s%n", printDuration(platformInfo.getStartupTime()))}, 2);
        addItem(new Object[]{"Uptime", format("%s%n", printDuration(platformInfo.getUptime()))}, 3);
    }

    public static String printDuration(double uptime) {

        // Not available
        if (uptime == 0 || uptime == -1) {
            return "N/A";
        }

        // Code taken from Karaf
        // https://svn.apache.org/repos/asf/felix/trunk/karaf/shell/commands/src/main/java/org/apache/felix/karaf/shell/commands/InfoAction.java

        NumberFormat fmtI = new DecimalFormat("###,###", new DecimalFormatSymbols(Locale.ENGLISH));
        NumberFormat fmtD = new DecimalFormat("###,##0.000", new DecimalFormatSymbols(Locale.ENGLISH));

        if (uptime < 1000) {
            return fmtI.format(uptime) + " ms";
        }
        uptime /= 1000;
        if (uptime < 60) {
            return fmtD.format(uptime) + " seconds";
        }
        uptime /= 60;
        if (uptime < 60) {
            long minutes = (long) uptime;
            return fmtI.format(minutes) + (minutes > 1 ? " minutes" : " minute");
        }
        uptime /= 60;
        if (uptime < 24) {
            long hours = (long) uptime;
            long minutes = (long) ((uptime - hours) * 60);
            String s = fmtI.format(hours) + (hours > 1 ? " hours" : " hour");
            if (minutes != 0) {
                s += " " + fmtI.format(minutes) + (minutes > 1 ? " minutes" : "minute");
            }
            return s;
        }
        uptime /= 24;
        long days = (long) uptime;
        long hours = (long) ((uptime - days) * 60);
        String s = fmtI.format(days) + (days > 1 ? " days" : " day");
        if (hours != 0) {
            s += " " + fmtI.format(hours) + (hours > 1 ? " hours" : "hour");
        }
        return s;
    }

}
