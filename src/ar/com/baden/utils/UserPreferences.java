package ar.com.baden.utils;

import javax.swing.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class UserPreferences {

    private static final Preferences PREFERENCES = Preferences.userNodeForPackage(UserPreferences.class);
    public static final String LAF_KEY = "lookAndFeel";
    public static final String DEFAULT_LAF = UIManager.getCrossPlatformLookAndFeelClassName();

    public static void saveUserTheme(String className) throws BackingStoreException {
        PREFERENCES.put(LAF_KEY, className);
        PREFERENCES.flush();
    }

    public static String getUserTheme() {
        return PREFERENCES.get(LAF_KEY, DEFAULT_LAF);
    }

}
