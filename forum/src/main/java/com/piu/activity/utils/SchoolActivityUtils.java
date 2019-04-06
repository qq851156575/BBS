package com.piu.activity.utils;

import com.piu.communion.utils.ImagesResultUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import static com.piu.base.utils.Constant.URL_SEPARATOR;

public class SchoolActivityUtils {

    /**
     * 获取图片真实路径
     * @return
     */
    public static String activityImgDirectory(){
        File file = new File(ImagesResultUtils.rootDirectory(),"static"+URL_SEPARATOR+"activity"+URL_SEPARATOR+"images");
        if(!file.exists()) file.mkdirs();
        return file.getAbsolutePath() + URL_SEPARATOR;
    }

    public static File activityImg(MultipartFile multipartFile){

        String fileName = multipartFile.getOriginalFilename();
        String fileNameExtension = fileName.substring(fileName.lastIndexOf("."));

        String realName = UUID.randomUUID().toString()+fileNameExtension;

        byte[] bytes=null;
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            bytes = multipartFile.getBytes();
            file = new File(activityImgDirectory() , realName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return file;
    }
}
