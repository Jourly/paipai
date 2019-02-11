package com.util;
import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
 
public class MyUtil {
    public int getMyCoin() {
        //1.创建客户端访问服务器的httpclient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //2.定义请求的url
        String url = "https://bizgw.jd.com/pcoinCommodity/pcoin";
        //3.以请求的连接地址创建get/post请求对象
        HttpGet get = new HttpGet(url);
        //如果有header请求，添加header请求
        get.addHeader("Content-Type","application/json;x-www-form-urlencoded;charset=UTF-8");
        get.addHeader("Referer","https://servicewechat.com/wx636ffa3eac393ccd/devtools/page-frame.html");
        get.addHeader("Cookie","guid=567f435d1d582d04bba42b4c3227bba60eebf9231c86dda126e741f6ae371465; lsid=vfx8ai8t45z4rftqa6b28jp5v12gw50s; pt_pin=jd_6c9c1e70713b4; pt_key=AAFcBm0DADCFH6Y2mHzxGETcqAU6XIkl5G9bdSKCfnhPlRp4yJPpQOPtPTivrhba0JtkTtkNA6I; pt_token=4wng8ho9");
        try {
            //4.向服务器发送请求，并获取返回数据
            CloseableHttpResponse response = httpClient.execute(get);        
            //获取返回的状态
            int status = response.getStatusLine().getStatusCode();
            System.out.println(status);
            //获取HttpEntity消息载体对象
            String responseString = EntityUtils.toString(response.getEntity());
    		JSONObject responseJson = JSON.parseObject(responseString);
    		//System.out.println(responseString);
    		String data = JsonAnalyze.getValueByJPath(responseJson, "data");
    		int dataInt = Integer.parseInt(data);
            System.out.println("data:" + data);
            //5.关闭连接
            httpClient.close();
            return dataInt;
        } catch (IOException e) {
            e.printStackTrace();
            //如果异常则返回-1
            return -1;
        }
    }
 
   
}