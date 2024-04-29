package org.example.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Order;
import org.example.entity.Result;
import org.example.entity.User;
import org.example.entity.UserOrderDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private RestTemplate restTemplate;
    @GetMapping("/getUserOrderById")
    public Result<UserOrderDTO>getUserOrderById(@RequestParam("userId") Integer userId){
        log.info("用户id:{}",userId);
        User user=new User();
        user.setName("王三");
        user.setId(userId);
        user.setAge(18);

        Result<Order> result=(Result<Order>) restTemplate.getForObject("http://localhost:12000/order/getByUserId?userId="+userId,Result.class);

        log.info("返回结果：{}",result);

        Map<String, Object> orderMap = (Map<String, Object>) result.getData();
        UserOrderDTO userOrderDTO=new UserOrderDTO();
        BeanUtils.copyProperties(user,userOrderDTO);
        userOrderDTO.setOrderId((Integer) orderMap.get("id"));
        userOrderDTO.setUserId((Integer) orderMap.get("userId"));


        return  Result.success(userOrderDTO,result.getMessage());
    }
}
