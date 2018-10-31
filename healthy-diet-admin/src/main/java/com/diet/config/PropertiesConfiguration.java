package com.diet.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author LiuYu
 */
@Component
@PropertySource(value = {"classpath:config/api-${spring.profiles.active}.properties"}, encoding = "utf-8")
public class PropertiesConfiguration {
}
