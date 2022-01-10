package com.github.bobcat33.PinApp;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

public class PinTools {

    public static boolean setPinned(boolean state, WinDef.HWND hwnd) {

        if (state) {
            if (!WindowTools.isMinimised(hwnd))
                User32.INSTANCE.SetForegroundWindow(hwnd);

            int flags = 1 | 2 | 64; // SWP_NOSIZE = 1 | SWP_NOMOVE = 2 | SWP_SHOWWINDOW = 64

            return LocalUser32.INSTANCE.SetWindowPos(hwnd, new WinDef.UINT_PTR(-1), 0, 0, 0, 0, new WinDef.UINT(flags));
        }

        else {
            int flags = 1 | 2; // SWP_NOSIZE = 1 | SWP_NOMOVE = 2

            return LocalUser32.INSTANCE.SetWindowPos(hwnd, new WinDef.UINT_PTR(-2), 0, 0, 0, 0, new WinDef.UINT(flags));

        }
    }

}
