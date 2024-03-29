# Pin-App
Pin-App is a Java app that allows users to select windows that they have open to be pinned in front of their other windows. When a window is pinned it will not be overlapped by any other open windows unless the other window is also pinned.

**\~\~ See [releases](https://github.com/bobcat33/PinApp/releases) for the executable Windows installer \~\~**

### Table of Contents
* [Installation](#install)
* [Usage](#usage)
* [Things to note](#toNote)

<a name="install"></a>
# Installation
* To install the app first download the dedicated installer from the latest release in [releases](https://github.com/bobcat33/PinApp/releases/latest), then run it.

* Follow the steps in the installer, then once it's installed the app will create a shortcut on your desktop as well as appear in your Windows app drawer.

![App shortcuts](images/install1.png)

* Run the app to make it appear in your system tray.

![App in system tray](images/install2.png)

* To add the app to your startup apps so that it opens every time you turn your computer on hover over the settings option and select **"Run Pin-App on startup"**.

![Add to startup](images/step4.png)

* If this does not work you can still follow [this guide](https://support.microsoft.com/en-us/windows/add-an-app-to-run-automatically-at-startup-in-windows-10-150da165-dcd9-7230-517b-cf3c295d89dd) to add the app to your startup apps manually.

## Updating
The process for updating Pin-App is the same as [installing](#install) with a few differences:
* **Do not use the same installer as the older version** - Ensure that you have the most up-to-date installer downloaded from [releases.](https://github.com/bobcat33/PinApp/releases/latest)
* During installation you will be given the option to choose a file path, like the first time you installed. So as to remove the older version from your system it is recommended that you select the same path that you installed the earlier version to. The default is `C:\Program Files\Pin-App`. If you select the same path you will be presented with the option below, click yes - this will remove the older version.

![Remove older version](images/install3.png)

* During install if you have the app open you will be presented with the window below. If you have any windows pinned at this stage it is recommended that you unpin them first. Select to close the applications and press OK. This is the faster way to install as it does not require a reboot.

![Close app](images/install4.png)

<a name="usage"></a>
# Usage
* To pin an app simply right click on the app in the system tray.

![App in system tray](images/step1.png)

* And select the desired app from the list.

![select the app](images/step2.png)

* Once selected the app window will be pinned above your other windows, to unpin it simply select it again or select "Unpin all pinned windows". The pinned windows are highlighted with a tick mark before their name.

![unpin the app](images/step3.png)

* You can report a bug or suggest a feature at any time from the settings menu.

![Settings](images/step4.png)

<a name="toNote"></a>
# Things to note
* **This will only work on Windows operating systems.** I have only tested it on Windows 10 and 11 but it is likely to work on previous (and future) versions of Windows too.
* The built in **"Run Pin-App on startup"** settings option will only be available if you are running it on Windows 10 or 11.
* I programmed this app using JDK 16.0.1 and the following libraries:
  - [JNA](https://github.com/java-native-access/jna#jna) (v5.10.0)
  - [JNA Platform](https://github.com/java-native-access/jna#jna-platform) (v5.10.0)
  - [mslinks](https://github.com/DmitriiShamrikov/mslinks) (v1.0.7)
* Some windows are not able to be pinned, I have listed the ones I know of below but if you find any more please let me know in [issues.](https://github.com/bobcat33/PinApp/issues)
  - Task Manager (Can be pinned from within Task Manager in its options dropdown menu by selecting "Always on top")
  - Windows Resource Manager
  - Windows Device Manager
* Occasionally an app might not be pinned, I am currently unsure why this happens but restarting Pin-App or closing and re-opening the app's window often helps. Another solution I have found that occasionally works is to pin and unpin windows file explorer then try again, this process may have to be repeated.
* I have not extensively tested this app, if you find any bugs / problems please report them in [issues](https://github.com/bobcat33/PinApp/issues) or [start a discussion.](https://github.com/bobcat33/PinApp/discussions/categories/bugs)
* I am fully open to critisism, if you think there are better ways of doing certain things or you have feature requests feel free to share them in [discussions](https://github.com/bobcat33/PinApp/discussions/categories/ideas) :)

[Back to Top ^](#Pin-App)
