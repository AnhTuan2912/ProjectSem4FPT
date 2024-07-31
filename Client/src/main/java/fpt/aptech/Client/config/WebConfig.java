/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Configuration.java to edit this template
 */
package fpt.aptech.Client.config;

import fpt.aptech.Client.component.UserStatusInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author Admin
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private UserStatusInterceptor userStatusInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/admin/**")
                .addResourceLocations("classpath:/static/admin/");
        registry.addResourceHandler("/static/user/**")
                .addResourceLocations("classpath:/static/user/");
        registry.addResourceHandler("/admin/images/**")
                .addResourceLocations("file:D:/FPT/ProjectSem4/Client/src/main/resources/static/admin/images/")
                .setCachePeriod(0); // Tắt cache

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Thêm interceptor vào registry
        registry.addInterceptor(userStatusInterceptor);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
