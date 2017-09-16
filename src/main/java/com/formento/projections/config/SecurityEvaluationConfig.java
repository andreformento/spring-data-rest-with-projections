package com.formento.projections.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

@Configuration
public class SecurityEvaluationConfig {

    // https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#data-configuration
    // dependency: org.springframework.security:spring-security-data
    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

}
