package org.devices.specifications.api.config;

import org.devices.specifications.api.model.Brand;
import org.devices.specifications.api.model.Model;
import org.devices.specifications.api.model.Specifications;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
public class CacheConfig {

    @Bean
    @Qualifier("brandsCache")
    public Map<String, Brand> createBrandsCache() {
        return new HashMap<>();
    }

    @Bean
    @Qualifier("modelsCache")
    public Map<String, Set<Model>> createModelsCache() {
        return new HashMap<>();
    }

    @Bean
    @Qualifier("specificationsCache")
    public Map<String, Specifications> createSpecificationsCache() {
        return new HashMap<>();
    }

}
