package com.kgc.hyb.walletprovider.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/26 10:34
 */
public class Base64Util {

    public static String encodeImgageToBase64(File filePath) throws IOException {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        FileInputStream inputFile = new FileInputStream(filePath);
        byte[] buffer = new byte[(int) filePath.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }

    /**
     * 将Base64位编码的图片进行解码，并保存到指定目录
     *
     * @param base64
     *            base64编码的图片信息
     * @return
     */
    public static void decodeBase64ToImage(String base64, String path,
                                           String imgName) {
        String newbase64=StringUtil.afterString(base64,"base64,");
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            FileOutputStream write = new FileOutputStream(new File(path
                    + imgName));
            byte[] decoderBytes = decoder.decodeBuffer(newbase64);
            write.write(decoderBytes);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

