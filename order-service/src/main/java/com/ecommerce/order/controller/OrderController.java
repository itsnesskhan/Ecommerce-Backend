package com.ecommerce.order.controller;

import com.ecommerce.order.dto.OrderRequestDTO;
import com.ecommerce.order.payloads.CommonResponse;
import com.ecommerce.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@RestController
public class OrderController {

    @Autowired
    private final OrderService orderService;
    @PostMapping("/create")
    ResponseEntity<?> createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        CommonResponse commonResponse = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.created(null).body(commonResponse);
    }

    @GetMapping("/user/{id}")
    ResponseEntity<?> getAllOrdersOfUser(@PathVariable long id){
        CommonResponse commonResponse = orderService.getAllOrdersByUser(id);
        return ResponseEntity.ok(commonResponse);
    }


}
