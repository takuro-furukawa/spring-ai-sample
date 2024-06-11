package com.example.springaisample;

import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAiSampleConfig {

    @Bean
    public OllamaApi getOllamaApi() {
        return new OllamaApi();
    }

}
