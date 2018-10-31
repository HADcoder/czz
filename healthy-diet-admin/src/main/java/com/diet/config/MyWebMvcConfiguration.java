package com.diet.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.diet.interceptor.JwtAuthInterceptor;
import com.diet.interceptor.RequestTimeInterceptor;
import io.undertow.UndertowOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import javax.servlet.MultipartConfigElement;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author LiuYu
 */
@Configuration
public class MyWebMvcConfiguration implements WebMvcConfigurer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${auth.url.ignore}")
    private String[] ignoreUrls;

    @Value("${spring.multipart.maxFileSize}")
    private String maxFileSize;

    @Value("${spring.multipart.maxRequestSize}")
    private String maxRequestSize;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:login.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
//        configurer.setUseSuffixPatternMatch(true).setUseTrailingSlashMatch(true);
    }

    /**
     * 拦截器
     *
     * @param registry 注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludeUrls = new ArrayList<>();
        excludeUrls.add("/*.html");
        excludeUrls.add("/favicon.ico");
        excludeUrls.add("/**/*.html");
        excludeUrls.add("/**/*.css");
        excludeUrls.add("/**/*.js");
        excludeUrls.add("/js/**");
        excludeUrls.add("/images/**");

        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
//        registry.addInterceptor(new RequestTimeInterceptor())
//                .excludePathPatterns(excludeUrls).addPathPatterns("/**");

        List<String> excludeUrls2 = new ArrayList<>();
        excludeUrls2.addAll(excludeUrls);
        excludeUrls2.addAll(new ArrayList<>(Arrays.asList(ignoreUrls)));
        registry.addInterceptor(jwtAuthInterceptor())
                .excludePathPatterns(excludeUrls2).addPathPatterns("/**");
    }

    @Bean
    public JwtAuthInterceptor jwtAuthInterceptor() {
        return new JwtAuthInterceptor();
    }

    /**
     * 配置fastJson
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat, SerializerFeature.SortField);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        fastConverter.setDefaultCharset(Charset.forName("UTF-8"));
        converters.add(fastConverter);
    }

    @Bean
    public UndertowServletWebServerFactory embeddedServletContainerFactory() {
        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
        // 这里也可以做其他配置
        factory.addBuilderCustomizers(builder -> builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true));
        return factory;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(maxFileSize);
        factory.setMaxRequestSize(maxRequestSize);
        return factory.createMultipartConfig();
    }
}
