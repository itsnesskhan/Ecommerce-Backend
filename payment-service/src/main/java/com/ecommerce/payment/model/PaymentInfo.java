package com.ecommerce.payment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PaymentInfo {

    @Id
    private String paymentId;
    private String paymentStatus;
    private String receipt;
    private Integer user_id;
    private String amount;
    private String order_id;
    private LocalDateTime createdOn;

}
