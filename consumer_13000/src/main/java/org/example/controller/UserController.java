package org.example.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.*;
import org.example.feign.ProviderService;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private DiscoveryClient discoveryClient;

    @Resource
    private ProviderService providerService;

    @GetMapping("/getUserOrderById")
//    @CircuitBreaker(name = "backendA",fallbackMethod = "down")
    public Result<UserOrderDTO>getUserOrderById(@RequestParam("userId") Integer userId){
        log.info("用户id:{}",userId);
        User user=new User();
        user.setName("王三");
        user.setId(userId);
        user.setAge(18);

//        List<ServiceInstance> serviceInstanceList=discoveryClient.getInstances("provider");
//        for (ServiceInstance s:serviceInstanceList
//        ) {
//            log.info("服务提供者ip:{} 端口号:{}",s.getHost(),s.getPort());
//        }
//        ServiceInstance serviceInstance=serviceInstanceList.get(0);
//
//
//        Result<Order> result=(Result<Order>) restTemplate.getForObject("http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/order/getByUserId?userId="+userId,Result.class);
       // Result<Order> result=(Result<Order>) restTemplate.getForObject("http://localhost:11000/order/getByUserId?userId="+userId,Result.class);
        Result<Order>result=providerService.getByUserId(userId);


        log.info("返回结果：{}",result);
        Order order=(Order) result.getData();
        UserOrderDTO userOrderDTO=new UserOrderDTO();
        BeanUtils.copyProperties(user,userOrderDTO);
        userOrderDTO.setOrderId( order.getId());
        userOrderDTO.setUserId( order.getUserId());


        return  Result.success(userOrderDTO,result.getMessage());
    }

    public Result<UserOrderDTO>down(Integer id,Throwable e){
        e.printStackTrace();
        UserOrderDTO userOrderDTO=new UserOrderDTO();
        return Result.success(userOrderDTO,"触发熔断");
    }

    @PostMapping("/create")
    public Result<String>create(@RequestBody UserOrderDTO userOrderDTO){
        //Post方法调用
        Result<String>result=providerService.createOrder(userOrderDTO);
        return result;
//        List<ServiceInstance> serviceInstanceList=discoveryClient.getInstances("provider");
//        for (ServiceInstance s:serviceInstanceList
//        ) {
//            log.info("服务提供者ip:{} 端口号:{}",s.getHost(),s.getPort());
//        }
//        ServiceInstance serviceInstance=serviceInstanceList.get(0);
//
//
//        //Post方法
//        Result<String>result=restTemplate.postForObject("http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/order/createOrder",userOrderDTO,Result.class);

    }

    @GetMapping("/update/{orderId}")
    public Result<String>update(@PathVariable("orderId") Integer orderId){
        providerService.updateOrder(orderId);
        Result<String>result=providerService.getOrder();
        return result;
//        List<ServiceInstance> serviceInstanceList=discoveryClient.getInstances("provider");
//        for (ServiceInstance s:serviceInstanceList
//        ) {
//            log.info("服务提供者ip:{} 端口号:{}",s.getHost(),s.getPort());
//        }
//        ServiceInstance serviceInstance=serviceInstanceList.get(0);
//
//
//        //Put方法
//        restTemplate.put("http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/order/updateOrder?orderId=" + orderId,null);
//
//        //Get方法
//        Result<List<Order>>result=restTemplate.getForObject("http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/order/getOrder",Result.class);
    }

    @GetMapping("/delete/{orderId}")
    public Result<String>delete(@PathVariable("orderId") Integer orderId){
        providerService.deleteOrder(orderId);
        Result<String>result=providerService.getOrder();
        return result;
//        List<ServiceInstance> serviceInstanceList=discoveryClient.getInstances("provider");
//        for (ServiceInstance s:serviceInstanceList
//        ) {
//            log.info("服务提供者ip:{} 端口号:{}",s.getHost(),s.getPort());
//        }
//        ServiceInstance serviceInstance=serviceInstanceList.get(0);
//
//
//        //Delete方法
//        restTemplate.delete("http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/order/deleteOrder?orderId="+orderId);
//        //Get方法
//        Result<List<Order>>result=(Result<List<Order>>)restTemplate.getForObject("http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/order/getOrder",Result.class);

    }
}
