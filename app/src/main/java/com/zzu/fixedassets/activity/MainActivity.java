package com.zzu.fixedassets.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zzu.fixedassets.R;
import com.zzu.fixedassets.app.App;
import com.zzu.fixedassets.entity.EventType;
import com.zzu.fixedassets.fragment.ExitDialogFragment;
import com.zzu.fixedassets.fragment.HealthFragment;
import com.zzu.fixedassets.fragment.HomeFragment;
import com.zzu.fixedassets.fragment.MsgFragment;
import com.zzu.fixedassets.fragment.UserFragment;
import com.zzu.fixedassets.utils.RxBus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AbstractBaseActivity implements View.OnClickListener, ExitDialogFragment.DialogOnClickListener {
    private static final int HOME_INDEX = 0;
    private static final int HEALTH_INDEX = 1;
    private static final int MSG_INDEX = 2;
    private static final int USER_INDEX = 3;

    private Fragment[] fragments;

    private ImageView img_tab0;
    private ImageView img_tab1;
    private ImageView img_tab2;
    private ImageView img_tab3;

    private TextView tv_tab0;
    private TextView tv_tab1;
    private TextView tv_tab2;
    private TextView tv_tab3;

    private RelativeLayout layout_tab0;
    private RelativeLayout layout_tab1;
    private RelativeLayout layout_tab2;
    private RelativeLayout layout_tab3;

    private int selectColor;
    private int unSelectColor;
    private int index;
    private int currentTabIndex;

    private CompositeDisposable mCompositeDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        initViews();
        initEvent();
        initDatas();
    }

    private void initViews() {
        img_tab0 = (ImageView) findViewById(R.id.img_tab0);
        img_tab1 = (ImageView) findViewById(R.id.img_tab1);
        img_tab2 = (ImageView) findViewById(R.id.img_tab2);
        img_tab3 = (ImageView) findViewById(R.id.img_tab3);

        tv_tab0 = (TextView) findViewById(R.id.tv_tab0);
        tv_tab1 = (TextView) findViewById(R.id.tv_tab1);
        tv_tab2 = (TextView) findViewById(R.id.tv_tab2);
        tv_tab3 = (TextView) findViewById(R.id.tv_tab3);

        layout_tab0 = (RelativeLayout) findViewById(R.id.layout_tab0);
        layout_tab1 = (RelativeLayout) findViewById(R.id.layout_tab1);
        layout_tab2 = (RelativeLayout) findViewById(R.id.layout_tab2);
        layout_tab3 = (RelativeLayout) findViewById(R.id.layout_tab3);

    }

    private void initEvent() {
        layout_tab0.setOnClickListener(this);
        layout_tab1.setOnClickListener(this);
        layout_tab2.setOnClickListener(this);
        layout_tab3.setOnClickListener(this);

    }

    private void initDatas() {
        mCompositeDisposable = new CompositeDisposable();
        selectColor = getResources().getColor(R.color.tab_text_color_pressed);
        unSelectColor = getResources().getColor(R.color.tab_text_color_normal);

        fragments = new Fragment[4];
        fragments[0] = HomeFragment.getInstance();
        fragments[1] = HealthFragment.getInstance();
        fragments[2] = MsgFragment.getInstance();
        fragments[3] = UserFragment.getInstance();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_content, fragments[0]).commit();

        //监听订阅事件
        Disposable d= RxBus.getInstance().toObservable().toObservable().subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        if(o instanceof EventType){
                            EventType e=(EventType)o;
                            Toast.makeText(MainActivity.this, e.getType(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });
        //subscription交给compositeSubscription进行管理，防止内存溢出
        mCompositeDisposable.add(d);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_tab0:
                index = HOME_INDEX;
                changeTabs(index);
                break;
            case R.id.layout_tab1:
                index = HEALTH_INDEX;
                changeTabs(index);
                break;
            case R.id.layout_tab2:
                index = MSG_INDEX;
                changeTabs(index);
                break;
            case R.id.layout_tab3:
                index = USER_INDEX;
                changeTabs(index);
                break;

        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_content, fragments[index]);
            }
            trx.show(fragments[index]).commitAllowingStateLoss();
        }
        currentTabIndex = index;

    }

    public void changeTabs(int i) {
        resetTabs();
        switch (i) {
            case HOME_INDEX:
                tv_tab0.setTextColor(selectColor);
                img_tab0.setImageResource(R.mipmap.icon_home_pressed);
                break;
            case HEALTH_INDEX:
                tv_tab1.setTextColor(selectColor);
                img_tab1.setImageResource(R.mipmap.icon_health_pressed);
                break;
            case MSG_INDEX:
                tv_tab2.setTextColor(selectColor);
                img_tab2.setImageResource(R.mipmap.icon_msg_pressed);
                break;
            case USER_INDEX:
                tv_tab3.setTextColor(selectColor);
                img_tab3.setImageResource(R.mipmap.icon_user_pressed);
                break;
        }

    }

    private void resetTabs() {
        tv_tab0.setTextColor(unSelectColor);
        img_tab0.setImageResource(R.mipmap.icon_home_normal);
        tv_tab1.setTextColor(unSelectColor);
        img_tab1.setImageResource(R.mipmap.icon_health_normal);
        tv_tab2.setTextColor(unSelectColor);
        img_tab2.setImageResource(R.mipmap.icon_msg_normal);
        tv_tab3.setTextColor(unSelectColor);
        img_tab3.setImageResource(R.mipmap.icon_user_normal);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            confirmExit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void confirmExit() {
        //退出操作
        ExitDialogFragment dialog = new ExitDialogFragment();
        dialog.show(getSupportFragmentManager(), "ExitDialog");

    }

    @Override
    public void onPositiveClickListener() {
        App.getInstance().exit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消所有订阅
        mCompositeDisposable.clear();
    }

}
