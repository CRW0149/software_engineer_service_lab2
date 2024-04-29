package org.example.feign;

import org.example.entity.Order;
import org.example.entity.Result;
import org.example.entity.UserOrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("provider")
public interface ProviderService {

     @GetMapping("/order/getByUserId")
     Result<Order>getByUserId(@RequestParam("userId") Integer userId);

     @GetMapping("/order/getOrder")
     Result<String> getOrder();

     @PostMapping("/order/createOrder")
     Result<String>createOrder(@RequestBody UserOrderDTO userOrderDTO);

     @PutMapping("/order/updateOrder")
     Result<String>updateOrder(@RequestParam("orderId") Integer orderId);

     @DeleteMapping("/order/deleteOrder")
     Result<String>deleteOrder(@RequestParam("orderId")Integer orderId);
}
