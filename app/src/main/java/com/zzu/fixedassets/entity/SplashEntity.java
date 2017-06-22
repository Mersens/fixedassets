package com.zzu.fixedassets.entity;

import java.io.Serializable;

/**
 * Created by Mersens on 2017/6/22 17:28
 * Email:626168564@qq.com
 */

public class SplashEntity implements Serializable{
    public  String savePath;
    public  String clickUrl;

    @Override
    public String toString() {
        return "SplashEntity{" +
                "savePath='" + savePath + '\'' +
                ", clickUrl='" + clickUrl + '\'' +
                '}';
    }



}
