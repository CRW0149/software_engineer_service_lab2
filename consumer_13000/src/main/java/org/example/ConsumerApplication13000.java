package org.example;

import org.example.rule.MyLoadBalanceConfig;
import org.example.rule.RandomLoadBalanceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
@LoadBalancerClient(name = "provider",configuration = MyLoadBalanceConfig.class)
public class ConsumerApplication13000 {
    @Bean
    public RestTemplate getRestTemplate (){
        return new RestTemplate();
    }
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication13000.class,args);
    }
}
