/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Configuration.java to edit this template
 */
package fpt.aptech.Client.config;

import fpt.aptech.Client.component.LocalDateTimeFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author Admin
 */
@Configuration
public class FormatConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new LocalDateTimeFormatter());
    }

}
