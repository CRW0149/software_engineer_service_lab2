package org.example.rule;

import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class MyLoadBalanceConfig {
    @Bean
    ReactorLoadBalancer randomLoadBalancer(Environment envir, LoadBalancerClientFactory lbf){
        String name = envir.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new MyLoadBalance(lbf.getLazyProvider(name, ServiceInstanceListSupplier.class),name);
    };
}
