package com.github.bobcat33.PinApp;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Main {
    public static String appName = "Pin-App";
    public static String pinErrorTitle = "Pin-App", pinErrorText = "An error occurred pinning that app, please try again";
    public static String unPinErrorTitle = "Pin-App", unPinErrorText = "An error occurred unpinning that app, please try again";
    public static TrayIcon trayIcon;

    public static void main(String[] args) {
        if (!SystemTray.isSupported()){
            windowMessageHyperlink("Pin-App  -  Warning", "Your system does not support this app!<br/>" +
                    "If you believe this is a bug please submit an issue to " +
                    "<a href=\"https://github.com/bobcat33/PinApp/issues\">https://github.com/bobcat33/PinApp/issues</a>", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SystemTray tray = SystemTray.getSystemTray();

        URL imageUrl = Main.class.getClassLoader().getResource("tray.gif");
        Image image;
        if (imageUrl != null)
            image = new ImageIcon(imageUrl).getImage();
        else
            image = Toolkit.getDefaultToolkit().getImage("tray.gif");

        PinPopupMenu popup = new PinPopupMenu();
        popup.generateDefaults();

        MouseListener mouseListener = new MouseListener() {

            public void mouseClicked(MouseEvent e) {}

            public void mouseEntered(MouseEvent e) {}

            public void mouseExited(MouseEvent e) {}

            public void mousePressed(MouseEvent e) {}

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popup.updatePinList();
                }
            }
        };

        trayIcon = new TrayIcon(image, Main.appName, popup);
        trayIcon.setImageAutoSize(true);
        trayIcon.addMouseListener(mouseListener);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            windowMessageHyperlink("Pin-App  -  Error", "ERROR: Failed to load app to system tray<br/>" +
                    "If you believe this is a bug please submit an issue to " +
                    "<a href=\"https://github.com/bobcat33/PinApp/issues\">https://github.com/bobcat33/PinApp/issues</a>", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void pingError(String caption, String text) {
        trayIcon.displayMessage(caption, text, TrayIcon.MessageType.ERROR);
    }

    public static void windowMessageHyperlink(String title, String text, int messageType) {
        JLabel label = new JLabel();
        Font font = label.getFont();

        String style = "font-family:" + font.getFamily() + "; font-size:" + font.getSize() + "pt;";
        JEditorPane contentComponent = new JEditorPane("text/html", "<html><body style=\"" + style + "\">"
                + text
                + "</body></html>");

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

        JOptionPane.showMessageDialog(null, contentComponent, title, messageType);
    }
}