package com.zzu.fixedassets.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mersens on 2017/5/12 14:50
 * Email:626168564@qq.com
 */

public class PreferenceUtil {
    private static PreferenceUtil sp;
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor editor;

    private static final String PREFERENCE_NAME = "_sharedinfo";
    private static final String IS_FIRST = "isFirst";

    public static synchronized PreferenceUtil getInstance(Context context) {
        if (sp == null) {
            sp = new PreferenceUtil();
            mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
            editor = mSharedPreferences.edit();
        }
        return sp;
    }
    public static Boolean isFirst() {
        return mSharedPreferences.getBoolean(IS_FIRST, true);
    }

    public static void setIsFirst(Boolean isIsFirst) {
        editor.putBoolean(IS_FIRST, isIsFirst);
        editor.commit();

    }
}
