package com.zzu.fixedassets.utils;

import com.zzu.fixedassets.app.Constans;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mersens on 2016/9/26.
 */

public class Node {
    //"<"节点转义
    private static String toStart(String name){
        return "&lt;"+name+"&gt;";
    }
    //">"节点转义
    private static String toEnd(String name){
        return "&lt;/"+name+"&gt;";
    }
    //请求参数拼接
    public static String getRequestParams(String namespace, Map<String,String> map){
        if(map==null){
            map=new HashMap<>();
        }
        StringBuffer sbf=new StringBuffer();
        sbf.append(Node.toStart("Request"));
        for(Map.Entry<String,String> entry:map.entrySet()){
            sbf.append(Node.toStart(entry.getKey()));
            sbf.append(entry.getValue());
            sbf.append(Node.toEnd(entry.getKey()));
        }
        sbf.append(Node.toEnd("Request"));
        String str="<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Header>\n" +
                "    <Identify xmlns=\"http://tempuri.org/\">\n" +
                "      <UserName>"+ Constans.USERNAME+"</UserName>\n" +
                "      <PassWord>"+Constans.PASSWORD+"</PassWord>\n" +
                "    </Identify>\n" +
                "  </soap:Header>\n" +
                "  <soap:Body>\n" +
                "    <"+namespace+" xmlns=\"http://tempuri.org/\">\n" +
                "      <str>"+sbf.toString()+"</str>\n" +
                "    </"+namespace+">\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>";
        return str;
    }
}
