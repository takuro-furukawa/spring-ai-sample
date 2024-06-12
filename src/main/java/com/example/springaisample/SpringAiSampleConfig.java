package com.example.springaisample;

import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAiSampleConfig {

    @Bean
    public OllamaApi getOllamaApi() {
        return new OllamaApi();
    }

    @Bean
    public org.neo4j.cypherdsl.core.renderer.Configuration cypherDslConfiguration() {
        return org.neo4j.cypherdsl.core.renderer.Configuration.newConfig()
                .withDialect(Dialect.NEO4J_5).build();
    }

}
