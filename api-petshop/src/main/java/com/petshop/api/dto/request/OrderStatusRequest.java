package com.petshop.api.dto.request;

import com.petshop.api.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderStatusRequest {

    @NotNull(message = "Status é obrigatório")
    private OrderStatus status;
}
