# Pin-App
Pin-App is a Java app that allows users to select windows that they have open to be pinned in front of their other windows. When a window is pinned it will not be overlapped by any other open windows unless the other window is also pinned.

**\~\~ I will be uploading a compiled JAR of the app and an executable soon \~\~**

**Table of Contents**
* [Usage](#usage)
* [Things to note](#toNote)

<a name="usage"></a>
# Usage
* To pin an app simply right click on the app in the system tray.

![App in system tray](images/step1.png)

* And select the desired app from the list.

![select the app](images/step2.png)

* Once selected the app window will be pinned above your other windows, to unpin it simply select it again or select "Unpin all pinned windows". The pinned windows are highlighted with a tick mark before their name.

![unpin the app](images/step3.png)

<a name="toNote"></a>
# Things to note
* **This will only work on Windows operating systems.** I have only tested it on Windows 10 but it is likely to work one previous (and future) versions of Windows too.
* I programmed this app using JDK 16.0.1 and the following libraries:
  - [JNA](https://github.com/java-native-access/jna#jna) (v5.10.0)
  - [JNA Platform](https://github.com/java-native-access/jna#jna-platform) (v5.10.0)
* Some windows are not able to be pinned, I have listed the ones I know of below but if you find any more please let me know in [issues.](https://github.com/bobcat33/PinApp/issues)
  - Task Manager (Can be pinned from within Task Manager in its options dropdown menu)
* Occasionally an app might not be pinned, I am currently unsure why this happens but restarting Pin-App or closing and re-opening the app's window often helps.
* I have not extensively tested this app, if you find any bugs / problems please report them in [issues](https://github.com/bobcat33/PinApp/issues) or [start a discussion.](https://github.com/bobcat33/PinApp/discussions/categories/bugs)
* I am fully open to critisism, if you think there are better ways of doing certain things or you have feature requests feel free to share them in [discussions](https://github.com/bobcat33/PinApp/discussions/categories/ideas) :)

[Back to Top ^](#Pin-App)
