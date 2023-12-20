package com.ecommerce.order.service;

import com.ecommerce.order.dto.OrderRequestDTO;
import com.ecommerce.order.dto.OrderResponseDTO;
import com.ecommerce.order.payloads.CommonResponse;

import java.util.List;

public interface OrderService {

    CommonResponse createOrder(OrderRequestDTO orderRequestDTO);

    CommonResponse getAllOrdersByUser(long user_id);
}
