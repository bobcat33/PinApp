package com.github.bobcat33.PinApp;

import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.User32;

import java.util.ArrayList;
import java.util.Arrays;

public class WindowTools {

    public static String getLabel(WinDef.HWND window) {

        char[] chars = new char[512];
        int length = User32.INSTANCE.GetWindowText(window, chars, 512);
        char[] titleChars = Arrays.copyOf(chars, length);
        String title = new String(titleChars);

        return StringUtils.formatTitle(title);

    }

    public static WinDef.HWND findWindow(String title) {
        return User32.INSTANCE.FindWindow(null, title);
    }

    public static ArrayList<WinDef.HWND> getWindows() {
        ArrayList<WinDef.HWND> windows = new ArrayList<>();

        User32.INSTANCE.EnumWindows((window, pointer) -> {
            char[] chars = new char[512];
            int length = User32.INSTANCE.GetWindowText(window, chars, 512);
            if (length != 0) {
                boolean isWindow = User32.INSTANCE.IsWindowVisible(window);

                if (isWindow) {
                    char[] classNameChars = new char[512];

                    int classNameLength = User32.INSTANCE.GetClassName(window, classNameChars, 512);

                    String className = new String(Arrays.copyOf(classNameChars, classNameLength));

                    if (!className.equals("Windows.UI.Core.CoreWindow") && !className.equals("Progman"))
                        windows.add(window);
                }
            }

            return true;
        }, null);

        return windows;
    }

    public static boolean isOpen(WinDef.HWND window) {
        return User32.INSTANCE.IsWindow(window);
    }

    public static boolean isMinimised(WinDef.HWND window) {
        return LocalUser32.INSTANCE.IsIconic(window);
    }
}
