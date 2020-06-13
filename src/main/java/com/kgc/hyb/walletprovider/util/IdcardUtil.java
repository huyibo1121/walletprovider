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
 * @Description 实名认证工具类
 * @date 2019/12/27 9:58
 */
public class IdcardUtil {
    public static String authentication(String idCard,String name)  {
        String host = "https://idcert.market.alicloudapi.com";
        String path = "/idcard";
        String appcode = "7fde20535d604345b0543aa8461bceef";
        String urlSend = host + path + "?idCard=" + idCard + "&name=" + name;
        try {
            URL url = new URL(urlSend);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Authorization", "APPCODE " + appcode);//格式Authorization:APPCODE (中间是英文空格)
            int httpCode = httpURLConnection.getResponseCode();
            System.out.println(httpCode);
            String json = read(httpURLConnection.getInputStream());
            System.out.println(json);
            return json;
        } catch (IOException e) {
            e.printStackTrace();
            //IOException
            return "03";
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
