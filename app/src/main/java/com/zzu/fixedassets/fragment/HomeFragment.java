package com.zzu.fixedassets.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zzu.fixedassets.R;
import com.zzu.fixedassets.activity.ImageActivity;
import com.zzu.fixedassets.entity.EventType;
import com.zzu.fixedassets.utils.Node;
import com.zzu.fixedassets.utils.RequestManager;
import com.zzu.fixedassets.utils.ResultObserver;
import com.zzu.fixedassets.utils.RxBus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by Mersens on 2017/5/12 11:36
 * Email:626168564@qq.com
 */

public class HomeFragment extends AbstractBaseFragment {
    private Button btn_request;
    private Button btn_img;
    private Button btn_comm;
    private TextView tv_msg;
    public RequestManager manager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_home, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

    }

    private void init(View view) {
        initViews(view);
        initEvent();
    }

    private void initViews(View view) {
        btn_request=(Button)view.findViewById(R.id.btn_request);
        btn_img=(Button)view.findViewById(R.id.btn_img);
        btn_comm=(Button)view.findViewById(R.id.btn_comm);
        tv_msg=(TextView)view.findViewById(R.id.tv_msg);
        manager=RequestManager.getInstance();
        view.findViewById(R.id.btn_registerandlogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegisterAndLogin();
            }
        });
    }



    private void initEvent() {
        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();

            }
        });
        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgIntent=new Intent(getActivity(), ImageActivity.class);
                startActivity(imgIntent);

            }
        });
        btn_comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBus.getInstance().send(new EventType("这是来自Fragment的消息"));
            }
        });
    }
    private void doRegisterAndLogin() {
        //获取新闻信息
        Map<String,String> map=new HashMap<>();
        map.put("PageSize",10+"");
        map.put("PageIndex",1+"");
        String request= Node.getRequestParams("NewsInquiry",map);
        RequestManager.getInstance()
                .mServiceStore
                .getNews(request)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
                        Log.e("TAG1","tag1===="+responseBody.string());

                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<ResponseBody, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(@NonNull ResponseBody responseBody) throws Exception {

                        return RequestManager.getInstance()
                                .mServiceStore
                                .download("");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {

                    @Override
                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
                        Log.e("TAG2","tag2===="+responseBody.string());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });

    }
    private void doLogin() {
        Map<String,String> map=new HashMap<>();
        map.put("PageSize",10+"");
        map.put("PageIndex",1+"");
        String request= Node.getRequestParams("NewsInquiry",map);
        RequestManager.getInstance()
                .mServiceStore
                .getNews(request)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultObserver(new RequestManager.onRequestCallBack() {
                    @Override
                    public void onSuccess(String msg) {
                        tv_msg.setText(msg);

                    }

                    @Override
                    public void onError(String msg) {
                        tv_msg.setText(msg);
                    }
                }));


/*
        ServiceStore service= manager.create(ServiceStore.class);
        Call<ResponseBody> call=service.getNews(request);
        manager.execute(call, new RequestManager.RequestCallBack() {
            @Override
            public void onSueecss(String msg) {
                Log.e("onSueecss",msg+"===========>>>");
                tv_msg.setText(msg);
            }

            @Override
            public void onError(String msg) {
                Log.e("onError",msg+"===========>>>");
                tv_msg.setText(msg);
            }

        });*/


    }

    public static Fragment getInstance() {
        return new HomeFragment();
    }
}
