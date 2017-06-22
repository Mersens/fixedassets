package com.zzu.fixedassets.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zzu.fixedassets.R;

/**
 * Created by Mersens on 2017/5/12 16:24
 * Email:626168564@qq.com
 */

public class ImageActivity extends AbstractBaseActivity {
    private ImageView imageView;
    private static final String URL="http://ehome.staging.topmd.cn:81/ueditor/net/upload/image/20160926/6361050831635160295248500.jpg";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        imageView=(ImageView)findViewById(R.id.imageView);
        Glide.with(this)
                .load(URL)
                .into(imageView);

    }
}
