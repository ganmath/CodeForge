package com.codeforge.config;

import com.codeforge.tenant.InMemoryTenantRepository;
import com.codeforge.tenant.TenantRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RepoConfig {

    @Bean
    @Primary
    @ConditionalOnProperty(name = "codeforge.mongo.enabled", havingValue = "false", matchIfMissing = false)
    public TenantRepository inMemoryTenantRepository() {
        return new InMemoryTenantRepository();
    }

    // When codeforge.mongo.enabled=true (default), Spring Data will provide a reactive Mongo implementation
}