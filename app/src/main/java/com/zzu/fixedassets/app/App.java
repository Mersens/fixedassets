package com.zzu.fixedassets.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mersens on 2017/5/12 10:34
 * Email:626168564@qq.com
 */

public class App extends Application {
    private static App sApp;
    private static List<Activity> mList = new LinkedList<Activity>();

    public static App getInstance() {
        if (sApp == null) {
            synchronized (App.class) {
                if (sApp == null) {
                    sApp = new App();
                }
            }
        }
        return sApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

                mList.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }


    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
