package com.zzu.fixedassets.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zzu.fixedassets.R;
import com.zzu.fixedassets.activity.MainActivity;
import com.zzu.fixedassets.utils.PreferenceUtil;

/**
 * Created by Mersens on 2017/5/12 15:11
 * Email:626168564@qq.com
 */

public class GuideFragment extends AbstractBaseFragment {
    private ImageView imageView;
    private int index;
    public static int[] imags = {R.mipmap.bg_guide1, R.mipmap.bg_guide2,
            R.mipmap.bg_guide3, R.mipmap.bg_guide4,};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        index = getArguments().getInt("params");
        imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(imags[index]);
        if(index==3){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                    PreferenceUtil.getInstance(getActivity()).setIsFirst(false);
                    getActivity().finish();

                }
            });
        }
    }
    public static Fragment getInstance(int params) {
        GuideFragment fragment = new GuideFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("params", params);
        fragment.setArguments(bundle);
        return fragment;

    }
}
