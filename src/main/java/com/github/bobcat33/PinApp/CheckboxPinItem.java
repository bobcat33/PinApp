package com.github.bobcat33.PinApp;

import com.sun.jna.platform.win32.WinDef;

import java.awt.CheckboxMenuItem;

public class CheckboxPinItem extends CheckboxMenuItem {

    private WinDef.HWND window;

    // Set the window handle that this CheckboxPinItem interacts with
    public void setWindow(WinDef.HWND window) {
        this.window = window;
        this.setLabel(WindowTools.getLabel(window));
    }

    // Override the setLabel function so that labels cannot be longer than the length defined in PinPopupMenu
    @Override
    public void setLabel(String title) {
        String label = title;
        if (title.length() > PinPopupMenu.getMaxLabelLength())
            label = title.substring(0, PinPopupMenu.getMaxLabelLength()) + PinPopupMenu.getLimitText();

        super.setLabel(label);
        super.setName(title);
    }

    public WinDef.HWND getWindow() {
        return window;
    }
}
