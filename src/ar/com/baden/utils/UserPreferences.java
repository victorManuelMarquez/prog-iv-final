package ar.com.baden.utils;

import javax.swing.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class UserPreferences {

    private static final Preferences PREFERENCES = Preferences.userNodeForPackage(UserPreferences.class);
    private static final String LAF_KEY = "lookAndFeel";
    private static final String CONFIRM_EXIT = "showClosingDialog";

    public static void saveUserTheme(String className) throws BackingStoreException {
        PREFERENCES.put(LAF_KEY, className);
        PREFERENCES.flush();
    }

    public static String getUserTheme() {
        return PREFERENCES.get(LAF_KEY, UIManager.getCrossPlatformLookAndFeelClassName());
    }

    public static void saveConfirmToExit(boolean status) throws BackingStoreException {
        PREFERENCES.putBoolean(CONFIRM_EXIT, status);
        PREFERENCES.flush();
    }

    public static boolean getConfirmToExit() {
        return PREFERENCES.getBoolean(CONFIRM_EXIT, true);
    }

}
