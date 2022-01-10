package com.github.bobcat33.PinApp;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class PinCheckListener implements ItemListener {

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            CheckboxPinItem item = (CheckboxPinItem) e.getItemSelectable();
            if (!PinTools.setPinned(true, item.getWindow())) {
                item.setState(false);
                Main.pingError(Main.pinErrorTitle, Main.pinErrorText);
            }
        }
        else if (e.getStateChange() == ItemEvent.DESELECTED) {
            CheckboxPinItem item = (CheckboxPinItem) e.getItemSelectable();
            if (!PinTools.setPinned(false, item.getWindow())) {
                item.setState(true);
                Main.pingError(Main.unPinErrorTitle, Main.unPinErrorText);
            }
        }
    }
}
