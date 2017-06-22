package com.zzu.fixedassets.service;

import com.zzu.fixedassets.entity.ResultsEntity;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by Mersens on 2016/9/28.
 */

public interface ServiceStore {
    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://tempuri.org/NewsInquiry"
    })
    @POST("WebServices/EhomeWebservice.asmx")
    Observable<ResponseBody> getNews(@retrofit2.http.Body String str);
    @GET
    Observable<ResponseBody> download(@Url String fileUrl);
    @GET("http://gank.io/api/data/{type}/{pageSize}/{pageIndex}")
    Observable<ResultsEntity> getInfo(@Path("type") String type, @Path("pageSize") String pageSize, @Path("pageIndex") String pageIndex);
}
