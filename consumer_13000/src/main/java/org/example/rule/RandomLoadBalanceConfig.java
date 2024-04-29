package org.example.rule;

import org.springframework.cloud.loadbalancer.core.*;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class RandomLoadBalanceConfig {
    @Bean
    ReactorLoadBalancer randomLoadBalancer(Environment envir, LoadBalancerClientFactory lbf){
        String name = envir.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new RandomLoadBalancer(lbf.getLazyProvider(name, ServiceInstanceListSupplier.class),name);
    };
}
