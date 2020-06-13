package com.kgc.hyb.walletprovider.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author 胡怡博
 * @version 0.0.1
 * @Description 描述
 * @date 2019/12/26 10:37
 */
public class Upload {
    public static String fileupload2(MultipartFile upload, HttpServletRequest request){
        String f = upload.getOriginalFilename();
        //截去文件后缀名
        //获取真实路径
        String s= request.getServletContext().getRealPath("/file/");
        String filename=s+ f;
        File file=new File(filename);

        try {
            upload.transferTo(file);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return f;
        /*  return filename;*/
    }
    public static String fileupload(MultipartFile upload, HttpServletRequest request){
        String f = upload.getOriginalFilename();
        //截去文件后缀名
        //生成随机文件名
        String u= UUID.randomUUID().toString()+f.substring(f.lastIndexOf("."));
        //获取真实路径
        String s= request.getServletContext().getRealPath("/file/");
        String filename=s+u;
        File file=new File(filename);
        try {
            upload.transferTo(file);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return filename;
    }

}
