package com.ecommerce.order.dto;

import com.ecommerce.order.entity.OrderItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {

    private List<OrderItemDTO> orderItems;

    private Long userId;

}
