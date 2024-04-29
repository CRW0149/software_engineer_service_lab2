package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Order;
import org.example.entity.Result;
import org.example.entity.UserOrderDTO;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @GetMapping("/getByUserId")
    public Result<Order>getByUserId(@RequestParam("userId") Integer userId){
        Order order=new Order();
        order.setId(1111);
        order.setUserId(userId);
        return Result.success(order);
    }

    /**
     * 得到订单
     * @return
     */
    @GetMapping("/getOrder")
    public Result<String>getOrder(){
        log.info("订单获取成功");
        return Result.success("订单获取成功");
    }


    /**
     * 创建订单
     * @param userOrderDTO
     * @return
     */
    @PostMapping("/createOrder")
    public Result<String>createOrder(@RequestBody UserOrderDTO userOrderDTO)
    {
        log.info("订单创建成功：{}",userOrderDTO);
        return Result.success("订单创建成功");
    }

    /**
     * 修改订单
     * @param orderId
     * @return
     */
    @PutMapping("/updateOrder")
    public Result<String>updateOrder(@RequestParam("orderId") Integer orderId){
        log.info("订单修改成功：{}",orderId);
        return Result.success("订单修改成功："+orderId);
    }

    /**
     * 删除订单
     * @param orderId
     * @return
     */
    @DeleteMapping("deleteOrder")
    public Result<String>deleteOrder(@RequestParam("orderId")Integer orderId){
        log.info("订单删除成功：{}",orderId);
        return Result.success("订单删除成功："+orderId);
    }
}
