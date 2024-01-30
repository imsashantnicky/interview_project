package com.example.interview_project.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private static final String PREF_NAME = "MySharedPreferences";
    private static final String KEY_FAVORITE_PREFIX = "favorite_";

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public MySharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveFavoriteState(int userId, boolean isFavorite) {
        editor.putBoolean(KEY_FAVORITE_PREFIX + userId, isFavorite);
        editor.apply();
    }

    public boolean getFavoriteState(int userId) {
        return sharedPreferences.getBoolean(KEY_FAVORITE_PREFIX + userId, false);
    }

}
