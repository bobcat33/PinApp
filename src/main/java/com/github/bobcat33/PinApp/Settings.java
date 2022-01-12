package com.github.bobcat33.PinApp;

import mslinks.ShellLinkException;
import mslinks.ShellLinkHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class Settings extends Menu {
    // TODO - Store all file paths as class variables in Main
    private boolean usingStartup;
    private StartupItem startupItem;

    public Settings(String label, String startup, String suggest, String report) {
        super(label);

        if (System.getProperty("os.name").equals("Windows 10") && new File(Main.exePath).exists()) {
            this.startupItem = new StartupItem(startup);
            this.usingStartup = true;
            add(startupItem);
            addSeparator();
        }

        MenuItem suggestItem = new MenuItem(suggest);
        suggestItem.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(URI.create(Main.suggestLink));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        add(suggestItem);

        MenuItem reportItem = new MenuItem(report);
        reportItem.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(URI.create(Main.issuesLink));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
        add(reportItem);

        update();
    }

    public void update() {
        if (this.usingStartup)
            if (!this.startupItem.update()) removeStartup();
    }

    public void removeStartup() {
        this.usingStartup = false;
        remove(startupItem);
    }

    private static class StartupItem extends CheckboxMenuItem {
        File startupDir = new File(Main.startupDirPath);
        File shortcut = new File(Main.shortcutPath);

        StartupItem(String label) {
            super(label);
            this.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (!startupDir.exists()) {
                        Main.windowMessageHyperlink(Main.appName + "  -  Error", "ERROR: Failed to create shortcut \"" +
                                Main.shortcutPath +
                                "\" of \"" + Main.exePath + "\"<br/>If you believe this is a bug please submit an issue to " +
                                "<a href=\"" + Main.issuesLink + "\">" + Main.issuesLink + "</a><br/><br/>" +
                                "<b>Full Error:</b><br/>The directory \"" + Main.startupDirPath + "\" does not exist.", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!new File(Main.exePath).exists()) {
                        Main.windowMessageHyperlink(Main.appName + "  -  Error", "ERROR: Failed to create shortcut \"" +
                                Main.shortcutPath +
                                "\" of \"" + Main.exePath + "\"<br/>If you believe this is a bug please submit an issue to " +
                                "<a href=\"" + Main.issuesLink + "\">" + Main.issuesLink + "</a><br/><br/>" +
                                "<b>Full Error:</b><br/>The directory \"" + Main.exePath + "\" does not exist.", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    try {
                        ShellLinkHelper.createLink(Main.exePath, Main.shortcutPath);
                    } catch (IOException | ShellLinkException exception) {
                        Main.windowMessageHyperlink(Main.appName + "  -  Error", "ERROR: Failed to create shortcut \"" +
                                Main.shortcutPath +
                                "\" of \"" + Main.exePath + "\"<br/>If you believe this is a bug please submit an issue to " +
                                "<a href=\"" + Main.issuesLink + "\">" + Main.issuesLink + "</a><br/><br/>" +
                                "<b>Full Error:</b><br/>" + exception, JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    if (!shortcut.delete())
                        Main.windowMessageHyperlink(Main.appName + "  -  Error", "ERROR: Failed to remove shortcut" +
                                "<br/>If you believe this is a bug please submit an issue to " +
                                "<a href=\"" + Main.issuesLink + "\">" + Main.issuesLink + "</a>", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        boolean update() {
            if (!startupDir.exists()) return false;
            setState(shortcut.exists());
            return true;
        }

    }

}
