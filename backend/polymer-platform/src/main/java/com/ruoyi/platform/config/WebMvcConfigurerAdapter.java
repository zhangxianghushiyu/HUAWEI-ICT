package com.ruoyi.platform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;



@Configuration
public class WebMvcConfigurerAdapter extends WebMvcConfigurationSupport {

    @Value("${file.profile}")
    private String profile;

    @Autowired
    private InterceptorConfig interceptorConfig;


    /***
     * addPathPatterns("/**"):拦截所有请求
     * excludePathPatterns： 不拦截的请求
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorConfig).addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html/**")
                //文件相关
                .excludePathPatterns("/file/**")
                .excludePathPatterns("/package/**")
                //公共控制器
                .excludePathPatterns("/common/**")
                //用户登录
                .excludePathPatterns("/platform/user/login")
                .excludePathPatterns("/platform/user/accountLogin")
                .excludePathPatterns("/platform/user/wxLogin")
                //用户注册
                .excludePathPatterns("/platform/user/registerAccount")
                //开关放行
                .excludePathPatterns("/platform/switchManage/get")
                //协议
                .excludePathPatterns("/platform/agreement/get")
                //广告配置
                .excludePathPatterns("/publicize/adConfig")
                //短剧工具数据转移
                .excludePathPatterns("/platform/dramaTool/dataTransfer")
                .excludePathPatterns("/platform/dramaTool/modifyUrl")
                //taku配置
                .excludePathPatterns("/publicize/taKuAdConfig")
                //taku回调
                .excludePathPatterns("/publicize/taKuCallback")
                //首页短剧信息
                .excludePathPatterns(
                        //短剧分类
                        "/platform/drama/getCategory",
                        //短剧查询
                        "/platform/drama/homePage",
                        "/platform/drama/query")
                //视频首页信息
                .excludePathPatterns("/platform/video/getList")
                //微信支付回调地址
                .excludePathPatterns("/platform/wx/payNotify")
                //支付宝支付回调地址
                .excludePathPatterns("/platform/ali/payNotify")
                //微信提现回调地址
                .excludePathPatterns("/platform/wx/transferBatchesNotify","/platform/wx/test")
                //广告回调地址
                .excludePathPatterns("/publicize/callback")
                .excludePathPatterns("/publicize/csjCallback")
                .excludePathPatterns(
                        "/platform/test/test",
                        "/platform/test/onlineTest"
                )
                .excludePathPatterns(
//                "/platform/ebook/saveRecord",
//                "/platform/ebook/deleteRecord",
//                        "/platform/ebook/getRecord"
        )
            ;
    }


    /***
     * 配置静态资源访问拦截
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html", "doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        //本地获取上传文件
        registry.addResourceHandler("/file/**").addResourceLocations("file:" + profile + "/");
        registry.addResourceHandler("/common/**").addResourceLocations("file:" + profile + "/");
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }


    /**
     * 跨域解决
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路由
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许证书（cookies）
                .allowCredentials(true)
                // 设置允许的方法
                .allowedMethods("*")
                // 跨域允许时间
                .maxAge(3600);
    }
}
