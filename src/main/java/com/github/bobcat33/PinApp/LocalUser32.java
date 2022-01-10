package com.github.bobcat33.PinApp;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;

public interface LocalUser32 extends StdCallLibrary {
    LocalUser32 INSTANCE = Native.load("user32", LocalUser32.class);

    /*
    * Set window position, this one accepts UINT_PTR as the hWndInsertAfter value as opposed to
    * User32#SetWindowPos which can only accept HWND
     */
    boolean SetWindowPos(WinDef.HWND hWnd, WinDef.UINT_PTR hWndInsertAfter, int X, int Y, int cx, int cy, WinDef.UINT uFlags);

    // User32 doesn't implement this method so I'm adding it here for convenience
    boolean IsIconic(WinDef.HWND hWnd);
}
