package org.example.rule;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class MyLoadBalance implements ReactorServiceInstanceLoadBalancer {
    private final String serviceId;
    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    public MyLoadBalance(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider, String serviceId) {
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
    }

    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = this.serviceInstanceListSupplierProvider.getIfAvailable();
        return supplier.get(request).next().map((serviceInstances) -> {
            return this.getInstanceResponse(serviceInstances);
        });
    }
    public Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {
        if (instances.isEmpty()) {
            return new EmptyResponse();
        }
        ServiceInstance serviceInstance = null;
        if(LocalDateTime.now().getMinute()%2==0){//偶数分钟第一台服务器
            serviceInstance=instances.get(0);
        }else {
            serviceInstance=instances.get(1);//奇数分钟第二台服务器
        }
        return new DefaultResponse(serviceInstance);
    }
}
