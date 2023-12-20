package com.ecommerce.order.service.impl;

import com.ecommerce.order.dto.*;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.entity.OrderItem;
import com.ecommerce.order.entity.OrderStatus;
import com.ecommerce.order.payloads.CommonResponse;
import com.ecommerce.order.repository.OrderItemRepository;
import com.ecommerce.order.repository.OrderRepository;
import com.ecommerce.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final OrderItemRepository orderItemRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final ObjectMapper objectMapper;

    @Autowired
    private final RestTemplate restTemplate;

    @Override
    public CommonResponse createOrder(OrderRequestDTO orderRequestDTO) {
        log.info("inside order-service create order method");
        AtomicReference<ProductResponseDTO> productWrapper = new AtomicReference<>();
        AtomicReference<UserDTO> userWrapper = new AtomicReference<>();
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        System.out.println(orderRequestDTO);
        List<OrderItem> orderItems = Arrays.asList(modelMapper.map(orderRequestDTO.getOrderItems(), OrderItem[].class));

        Long totalAmount = orderItems.stream().map(item -> {
            CommonResponse response = restTemplate.getForObject("http://localhost:8081/api/v1/product/{id}", CommonResponse.class, item.getProductId());
            Object data = response.getData();
            ProductResponseDTO product = objectMapper.convertValue(data, ProductResponseDTO.class);
            log.info("product data : {} ",product);
            productWrapper.set(product);

            CommonResponse userResponse = restTemplate.getForObject("http://localhost:8082/api/v1/user/get/{id}", CommonResponse.class, orderRequestDTO.getUserId());
            Object userData = userResponse.getData();
            log.info("user data : {} ",userData);
            UserDTO userDTO = objectMapper.convertValue(userData, UserDTO.class);
            System.out.println("user dto : "+userDTO);
            userWrapper.set(userDTO);

            OrderItemResponse orderItem = OrderItemResponse.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .price(product.getDisPrice())
                    .quantity(item.getQuantity())
                    .build();
            orderItemResponses.add(orderItem);
            
            item.setUnitPrice(product.getDisPrice());
            return product.getDisPrice() * item.getQuantity();
        }).reduce(0L, Long::sum);


//        payment processing




//      created order
        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .status(OrderStatus.PENDING.name())
                .orderItems(orderItems)
                .userId(orderRequestDTO.getUserId())
                .totalAmount(totalAmount)
                .build();

//      set order in order items
        for (OrderItem item:orderItems) {
            item.setOrder(order);
        }

        order = orderRepository.save(order);

        UserDTO userDTO = userWrapper.get();

        OrderResponseDTO orderResponseDTO = OrderResponseDTO.builder()
                .id(order.getId())
                .username(userDTO.getFname()+" "+userDTO.getLname())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .userId(order.getUserId())
                .orderItems(orderItemResponses)
                .status(order.getStatus())
                .build();

        return CommonResponse.builder()
                        .message("Order created successfully!")
                        .data(orderResponseDTO)
                        .httpStatus(HttpStatus.CREATED)
                        .build();
    }

    @Override
    public CommonResponse getAllOrdersByUser(long user_id) {
        List<Order> allOrdersByUserId = orderRepository.getAllOrdersByUserId(user_id);
        List<OrderResponseDTO> list = Arrays.asList(modelMapper.map(allOrdersByUserId, OrderResponseDTO[].class));
        return CommonResponse.builder().message("All order of user with id: "+user_id).data(list).httpStatus(HttpStatus.OK).build();
    }
}
