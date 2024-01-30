package com.example.interview_project.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.interview_project.models.FavoriteUserDataModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static final String PREF_NAME = "favorite_users";
    private static final String KEY_FAVORITE_USERS = "favorite_users_list";

    public static void saveFavoriteUserList(Context context, List<FavoriteUserDataModel> favoriteUserList) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(favoriteUserList);
        editor.putString(KEY_FAVORITE_USERS, json);
        editor.apply();
    }

    public static List<FavoriteUserDataModel> getFavoriteUserList(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString(KEY_FAVORITE_USERS, null);
        Type type = new TypeToken<ArrayList<FavoriteUserDataModel>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
