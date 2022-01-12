package com.github.bobcat33.PinApp;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;

public class Main {
    public static String appName = "Pin-App";
    public static String pinErrorTitle = appName, pinErrorText = "An error occurred pinning that app, please try again";
    public static String unPinErrorTitle = appName, unPinErrorText = "An error occurred unpinning that app, please try again";
    public static String issuesLink = "https://github.com/bobcat33/PinApp/issues", suggestLink = "https://github.com/bobcat33/PinApp/discussions/categories/ideas";

    public static String startupDirPath = System.getProperty("user.home") + "\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";
    public static String shortcutPath = startupDirPath + "\\Pin-App.lnk";
    public static String exePath = System.getProperty("user.dir") + "\\Pin-App.exe";

    public static TrayIcon trayIcon;

    public static void main(String[] args) {
        // Ensure that the system supports the system tray as this is essential
        if (!SystemTray.isSupported()){
            windowMessageHyperlink(Main.appName + "  -  Warning", "Your system does not support this app!" +
                    "<br/>If you believe this is a bug please submit an issue to " +
                    "<a href=\""+ issuesLink + "\">"+ issuesLink + "</a>", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Check if the lock file directory exists, if not create it
        String appFiles = System.getProperty("user.home") + "\\.PinApp";
        File dir = new File(appFiles);
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                windowMessageHyperlink(Main.appName + "  -  Error", "ERROR: Failed to create essential " +
                        "directory \"" + appFiles + "\"<br/>" +
                        "This may be the result of a bug - please submit an issue to " +
                        "<a href=\"" + issuesLink + "\">" + issuesLink + "</a>", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Create the lock file if it doesn't exist and test it to ensure that the app isn't already running
        File file = new File(appFiles, "pin_app.lock");

        try {
            FileChannel fc = FileChannel.open(file.toPath(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE);
            FileLock lock = fc.tryLock();
            if (lock == null) {
                windowMessageHyperlink(Main.appName, Main.appName + " is already open!<br/>" +
                        "Check the system tray in the bottom right of the screen," +
                        " you may have to press \"Show hidden icons\".", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (IOException e) {
            windowMessageHyperlink(Main.appName + "  -  Error", "ERROR: Failed to load lock file<br/>" +
                    "This may be the result of a bug - please submit an issue to " +
                    "<a href=\"" + issuesLink + "\">" + issuesLink + "</a><br/><br/>" +
                    "<b>Full Error:</b><br/>" + e, JOptionPane.ERROR_MESSAGE);
            return;
        }

        SystemTray tray = SystemTray.getSystemTray();

        // Load the image to use as the tray icon from resources
        URL imageUrl = Main.class.getClassLoader().getResource("tray.gif");
        Image image;
        if (imageUrl != null)
            image = new ImageIcon(imageUrl).getImage();
        else
            image = Toolkit.getDefaultToolkit().getImage("tray.gif"); // Loads blank image

        // Create a new popup menu for the system tray app, initialise variables and run startup tasks
        PinPopupMenu popup = new PinPopupMenu();
        popup.generateDefaults();

        MouseListener mouseListener = new MouseListener() {

            public void mouseClicked(MouseEvent e) {}

            public void mouseEntered(MouseEvent e) {}

            public void mouseExited(MouseEvent e) {}

            public void mousePressed(MouseEvent e) {}

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    // When the user opens the popup menu refresh the menu contents
                    popup.update();
                }
            }
        };

        trayIcon = new TrayIcon(image, Main.appName, popup);
        trayIcon.setImageAutoSize(true);
        trayIcon.addMouseListener(mouseListener);

        // Attempt to add the tray icon to the system tray, if fails display error message and close app
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            windowMessageHyperlink(Main.appName + "  -  Error", "ERROR: Failed to load app to system tray" +
                    "<br/>If you believe this is a bug please submit an issue to " +
                    "<a href=\"" + issuesLink + "\">" + issuesLink + "</a><br/><br/>" +
                    "<b>Full Error:</b><br/>" + e, JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void pingError(String caption, String text) {
        // Display a Windows notification error popup
        trayIcon.displayMessage(caption, text, TrayIcon.MessageType.ERROR);
    }

    public static void windowMessageHyperlink(String title, String text, int messageType) {
        JLabel label = new JLabel();
        Font font = label.getFont();

        // Generate style based on the default font and size and insert values into html template
        String style = "font-family:" + font.getFamily() + "; font-size:" + font.getSize() + "pt;";
        JEditorPane contentComponent = new JEditorPane("text/html", "<html><body style=\"" + style + "\">"
                + text
                + "</body></html>");

        // Create a listener for any links that might exist in the JEditorPane
        contentComponent.addHyperlinkListener(e -> {
            if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                try {
                    Desktop.getDesktop().browse(e.getURL().toURI());
                } catch (IOException | URISyntaxException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        contentComponent.setEditable(false);
        contentComponent.setBackground(label.getBackground());

        // Display to the user
        JOptionPane.showMessageDialog(null, contentComponent, title, messageType);
    }
}
