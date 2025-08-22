package com.example.config;

import com.netflix.discovery.EurekaClientOptionalArgs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EurekaClientConfig {

    @Bean
    public EurekaClientOptionalArgs discoveryClientOptionalArgs() {
        return new EurekaClientOptionalArgs();
    }

}
