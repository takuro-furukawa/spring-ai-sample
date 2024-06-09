package com.example.springaisample;

import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestClient;

@Configuration
public class SpringAiSampleConfig {

    @Bean
    public OllamaApi getOllamaApi() {
        RestClient.Builder builder = RestClient.builder();
        return new OllamaApi("http://localhost:11434/", builder);
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(DataSourceProperties databaseProperties) {
        return new JdbcTemplate(
                DataSourceBuilder.create()
                        .url(databaseProperties.getUrl())
                        .username(databaseProperties.getUsername())
                        .password(databaseProperties.getPassword())
                        .build());
    }
}
