package com.zzu.fixedassets.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzu.fixedassets.R;

/**
 * Created by Mersens on 2017/5/12 11:36
 * Email:626168564@qq.com
 */

public class UserFragment extends AbstractBaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_user,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public static Fragment getInstance(){
        return new UserFragment();
    }
}
