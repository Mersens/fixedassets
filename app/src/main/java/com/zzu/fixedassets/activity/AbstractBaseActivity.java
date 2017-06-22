package com.zzu.fixedassets.activity;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.zzu.fixedassets.R;
import com.zzu.fixedassets.utils.StatusBarUtil;

/**
 * Created by Mersens on 2017/5/12 10:32
 * Email:626168564@qq.com
 */

public class AbstractBaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        changeStatusColor(getResources().getColor(R.color.status_color));
    }

    protected void changeStatusColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarUtil statusBarBackground = new StatusBarUtil(
                    this, color);
            statusBarBackground.setStatusBarColor();
        }
    }
}
