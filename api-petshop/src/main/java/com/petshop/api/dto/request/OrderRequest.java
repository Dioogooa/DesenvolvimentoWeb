package com.petshop.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    @NotNull(message = "Cliente é obrigatório")
    private Long customerId;

    @NotEmpty(message = "O pedido deve ter pelo menos um item")
    @Valid
    private List<OrderItemRequest> items;

    private String notes;
}
