package com.zzu.fixedassets.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.zzu.fixedassets.R;
import com.zzu.fixedassets.entity.Results;
import com.zzu.fixedassets.entity.ResultsEntity;
import com.zzu.fixedassets.utils.RequestManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Mersens on 2017/5/12 14:42
 * Email:626168564@qq.com
 */

public class SplashActivity extends Activity {
    private static final long SPLASH_DELAY_SECONDS = 3;
    private ImageView mImageView;
    private Button mButton;
    private Disposable mIntervalDisposable;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        mCompositeDisposable = new CompositeDisposable();
        mImageView = (ImageView) findViewById(R.id.img_splash);
        mButton = (Button) findViewById(R.id.btn_num);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainActivity();
            }
        });
        RequestManager.getInstance()
                .mServiceStore
                .getInfo("福利","10","1")
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultsEntity>() {
                    @Override
                    public void accept(@NonNull ResultsEntity results) throws Exception {
                      if(results.getResults()!=null && results.getResults().size()>0){
                          checkDownLoad(results.getResults().get(0));
                      }
                    }
                });

/*
        Disposable mIntervalDisposable = Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(SPLASH_DELAY_SECONDS+1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return SPLASH_DELAY_SECONDS - aLong;
                    }
                }).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        if(aLong==0){
                            mButton.setVisibility(View.GONE);
                            goMainActivity();
                        }else {
                            mButton.setVisibility(View.VISIBLE);
                            mButton.setText("跳过("+aLong+"s)");
                        }
                    }
                });
        mCompositeDisposable.add(mIntervalDisposable);*/

    }


    private void goMainActivity(){
        Intent intent=new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void checkDownLoad(Results result){


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!mIntervalDisposable.isDisposed()){
            mIntervalDisposable.dispose();
        }
        mCompositeDisposable.clear();

    }
}
