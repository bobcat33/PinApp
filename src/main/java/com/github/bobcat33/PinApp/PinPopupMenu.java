package com.github.bobcat33.PinApp;

import com.sun.jna.platform.win32.WinDef;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PinPopupMenu extends PopupMenu {
    private final int minIndex = 2;

    private ArrayList<CheckboxPinItem> windows = new ArrayList<>();

    private final MenuItem unPinAll = new MenuItem("Unpin all pinned windows");
    private final MenuItem noWindows = new MenuItem("There are currently no active windows...");
    private final Settings settings = new Settings("Settings", "Run " + Main.appName + " on startup", "Suggest a feature", "Report a bug");
    private final MenuItem exitItem = new MenuItem("[X] Close " + Main.appName);

    public void generateDefaults() {
        this.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));

        ActionListener exitListener = e -> {
            removeAllPins();
            System.exit(0);
        };

        ActionListener unPinAllListener = e -> {
            flushClosedPins();
            unPinAll();
        };

        unPinAll.addActionListener(unPinAllListener);
        unPinAll.setEnabled(false);
        this.add(unPinAll);
        this.addSeparator();

        displayNoWindows();

        this.addSeparator();

        this.add(settings);

        exitItem.addActionListener(exitListener);
        this.add(exitItem);
    }


    public void refreshPinNames() {
        for (CheckboxPinItem pin : windows) {
            String realLabel = WindowTools.getLabel(pin.getWindow());
            if (!realLabel.equals(pin.getLabel()))
                pin.setLabel(realLabel);
        }
    }

    public void addPin(WinDef.HWND window) {

        if (windows.size() == 0) this.remove(minIndex);

        CheckboxPinItem addToggle = new CheckboxPinItem();
        addToggle.setWindow(window);
        addToggle.addItemListener(new PinCheckListener());
        this.insert(addToggle, minIndex);

        windows.add(addToggle);

    }

    public void removeAllPins() {
        for (CheckboxPinItem window : windows) {
            if (window.getState())
                PinTools.setPinned(false, window.getWindow());
            this.remove(window);
        }

        windows = new ArrayList<>();

        displayNoWindows();

    }

    public void flushClosedPins() {
        ArrayList<CheckboxPinItem> remove = new ArrayList<>();

        for (CheckboxPinItem window : windows) {
            if (!WindowTools.isOpen(window.getWindow())) {
                PinTools.setPinned(false, window.getWindow());
                remove(window);
                remove.add(window);
            }
        }

        windows.removeAll(remove);

        if (windows.size() == 0) {
            displayNoWindows();
        }

    }

    public boolean contains(WinDef.HWND window) {
        for (CheckboxPinItem item : windows)
            if (WindowTools.getLabel(item.getWindow()).equals(WindowTools.getLabel(window)))
                return true;

        return false;
    }

    public void update() {
        ArrayList<WinDef.HWND> openWindows = WindowTools.getWindows();

        this.flushClosedPins();

        this.refreshPinNames();

        for (WinDef.HWND window : openWindows) {
            if (!this.contains(window))
                this.addPin(window);
        }

        updateUnPinAll();

        settings.update();
    }

    public void setPinnedState(boolean pinned, CheckboxPinItem pin) {
        if (pin.getState() == pinned) return;

        pin.setState(pinned);
        PinTools.setPinned(pinned, pin.getWindow());

    }

    public void setPinnedState(boolean pinned, WinDef.HWND window) {
        this.update();

        for (CheckboxPinItem item : windows) {
            if (WindowTools.getLabel(item.getWindow()).equals(WindowTools.getLabel(window))) {
                setPinnedState(pinned, item);
                return;
            }
        }
    }

    public void unPinAll() {
        for (CheckboxPinItem pin : windows)
            setPinnedState(false, pin);
    }


    private void displayNoWindows() {
        noWindows.setEnabled(false);
        this.insert(noWindows, minIndex);
    }

    private void updateUnPinAll() {
        unPinAll.setEnabled(false);
        for (CheckboxPinItem window : windows) {
            if (window.getState()) {
                unPinAll.setEnabled(true);
            }
        }
    }
}
