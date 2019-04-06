package com.piu.communion.utils;

import com.piu.communion.entity.ImagesResult;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.piu.base.utils.Constant.URL_SEPARATOR;

public class ImagesResultUtils {


    /**
     * 转富文本图片上传json格式
     *
     * @param object
     * @return
     */
    public static ImagesResult success(String[] object) {
        ImagesResult imagesResult = new ImagesResult();
        imagesResult.setErrno(0);
        imagesResult.setData(object);
        return imagesResult;
    }


    /**
     * 富文本图片临时路径转存永久路径
     *
     * @param src
     * @return
     */
    public static String imagesTransfer(String src) {

        InputStream input = null;
        OutputStream output = null;
        String[] fileName = src.split("/");
        String pathName = uploadTemp() + fileName[4];
        String dest = uploadImages()+ fileName[4];
        try {
            input = new FileInputStream(pathName);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null)
                    input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (output != null)
                    output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "/piu/upload/images/" + fileName[4];
    }

    /**
     * 正则表达式替换img标签图片路径
     *
     * @param html 富文本内容
     */
    public static String imagesPathReplacement(String html) {
        Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
        Matcher m_img = p_img.matcher(html);
        StringBuffer sb = new StringBuffer();
        boolean result_img = m_img.find();
        if (result_img) {
            while (result_img) {
                //获取到匹配的<img />标签中的内容
                String str_img = m_img.group(2);
                //开始匹配<img />标签中的src
                Pattern p_src = Pattern.compile("(src|SRC)=([\"'])(.*?)([\"'])");
                Matcher m_src = p_src.matcher(str_img);
                StringBuffer sbReplace = new StringBuffer("<img ");
                if (m_src.find()) {
                    String str_src = m_src.group(3);
                    m_src.appendReplacement(sbReplace, "src=\"" + imagesTransfer(str_src) + "\"");
                }
                m_src.appendTail(sbReplace);
                m_img.appendReplacement(sb, sbReplace.toString());
                //匹配html中是否存在下一个<img />标签，有则继续以上步骤匹配<img />标签中的src
                result_img = m_img.find();
            }
        }
        m_img.appendTail(sb);
        return sb.toString();
    }


    public static void delete (File file) { //递归删除文件

        Long ftime = file.lastModified();
        Date now = new Date();
        Long nowTime = now.getTime();
        Long ms = nowTime - ftime;
        /*
         * 若是文件则直接删除
         */

        File[] subs = file.listFiles();
        for (File sub : subs) {
            if (ms > 1000 * 60 * 60 * 24) {//文件保留时间超过24小时
                System.out.println(ms);
                sub.delete();//递归删除文件方法
            }

        }
    }

    /**
     * 获取项目根目录
     *
     * @return
     */
    public static File rootDirectory() {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!path.exists()) path = new File("");
        return path;
    }

    /**
     * 获取图片上传真实路径
     * @return
     */
    public static String uploadImages() {
        File uploadImages = new File(rootDirectory().getAbsolutePath(), "static"+URL_SEPARATOR+"upload"+URL_SEPARATOR+"images");
        if(!uploadImages.exists()) uploadImages.mkdirs();
        return uploadImages.getAbsolutePath() + URL_SEPARATOR;
    }

    /**
     * 获取图片上传临时真实路径
     * @return
     */
    public static String uploadTemp() {
        File uploadTemp = new File(rootDirectory().getAbsolutePath(), "static"+URL_SEPARATOR+"upload"+URL_SEPARATOR+"images"+URL_SEPARATOR+"temp");
        if(!uploadTemp.exists()) uploadTemp.mkdirs();
        return uploadTemp.getAbsolutePath() + URL_SEPARATOR;
    }
}


