package com.weshopify.platform.config;

import java.util.List;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Component
public class CORSCustomizer {
    public void corsCustomizer(HttpSecurity http) throws Exception{
        http.cors(c->{
            CorsConfigurationSource source=s->{
                CorsConfiguration cc=new CorsConfiguration();
                cc.setAllowCredentials(true);
                cc.setAllowedOrigins(List.of("*"));
              //  cc.setAllowedOrigins(List.of("localhost:3000"));
                cc.setAllowedHeaders(List.of("*"));
                cc.setAllowedMethods(List.of("*"));
              // cc.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                return  cc;
            };
            c.configurationSource(source);
        });
    }
}
