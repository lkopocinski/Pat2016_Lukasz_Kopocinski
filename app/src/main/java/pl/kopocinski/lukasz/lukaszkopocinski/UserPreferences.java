package pl.kopocinski.lukasz.lukaszkopocinski;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {
    private static final String PREFERENCE_FILE_KEY = "pl.kopocinski.lukasz.lukaszkopocinski.USER_PREFERENCES";
    public static final boolean USER_LOGGED_IN = true;
    public static final boolean USER_LOGGED_OUT = false;

    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor preferencesEditor;

    public static UserPreferences getInstance(Context context) {
        UserPreferences userPreferences = new UserPreferences(context);
        userPreferences.sharedPreferences = userPreferences.getSharedPreferencesPrivateMode();
        userPreferences.preferencesEditor = userPreferences.getEditor();

        return userPreferences;
    }

    private UserPreferences(Context context) {
        this.context = context;
    }

    public SharedPreferences getSharedPreferencesPrivateMode() {
        return context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
    }

    public SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }

    public void saveLoginStatus(boolean status) {
        preferencesEditor.putBoolean(getIsUserLoggedKey(), status);
        preferencesEditor.commit();
    }

    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(getIsUserLoggedKey(), USER_LOGGED_OUT);
    }

    private String getIsUserLoggedKey() {
        return context.getString(R.string.is_user_logged);
    }
}
