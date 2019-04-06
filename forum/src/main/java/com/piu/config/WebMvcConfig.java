package com.piu.config;

import com.piu.activity.utils.SchoolActivityUtils;
import com.piu.communion.utils.ImagesResultUtils;
import com.piu.communion.utils.UploadUtils;
import com.piu.filter.ImgFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

public class WebMvcConfig implements WebMvcConfigurer{
	//头像拦截器
	@Autowired
	private ImgFilter imgFilter;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	//将/login绑定到login
        /*registry.addViewController("/").setViewName("base/home");
        registry.addViewController("/home").setViewName("base/home");*/
        registry.addViewController("/index").setViewName("sys/index");
        registry.addViewController("/category").setViewName("communion/category");
        registry.addViewController("/softwasreDetails").setViewName("download/softwareDetails");
    }
    //每次将头像放入导航栏中
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(imgFilter).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/temp/**").addResourceLocations("file:"+ ImagesResultUtils.uploadTemp());
        registry.addResourceHandler("/upload/images/**").addResourceLocations("file:"+ ImagesResultUtils.uploadImages());
        registry.addResourceHandler("/upload/files/images/**").addResourceLocations("file:"+ UploadUtils.uploadDirectory());
        registry.addResourceHandler("/upload/activity/images/**").addResourceLocations("file:"+ SchoolActivityUtils.activityImgDirectory());
    }
}
