package com.kgc.hyb.walletprovider.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/27 16:19
 */
public class BankcardUtil {

    //银行卡号   身份证号   手机号  姓名
    public static String bankVerification(String accountNo,String idCard,String mobile,String name){
        String host = "https://bankali.market.alicloudapi.com";
        String path = "/bankCheck4New";
        String appcode = "7fde20535d604345b0543aa8461bceef";
        try {
            String urlSend = host + path + "?accountNo=" + accountNo + "&idCard=" + idCard+"&mobile="+mobile+"&name="+name;
            URL url = new URL(urlSend);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Authorization", "APPCODE " + appcode);//格式Authorization:APPCODE (中间是英文空格)
            int httpCode = httpURLConnection.getResponseCode();
            String json = read(httpURLConnection.getInputStream());
            System.out.println("/* 获取服务器响应状态码 200 正常；400 权限错误 ； 403 次数用完； */ ");
            System.out.println(httpCode);
            System.out.println("/* 获取返回的json   */ ");
            System.out.print(json);
            return json;
        } catch (IOException e) {
            e.printStackTrace();
            //IOException
            return "IOException";
        }
    }
    /*
        读取返回结果
     */
    private static String read(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            line = new String(line.getBytes(), "utf-8");
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}

