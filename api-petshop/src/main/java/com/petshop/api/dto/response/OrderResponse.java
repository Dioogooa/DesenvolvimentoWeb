package com.petshop.api.dto.response;

import com.petshop.api.model.Order;
import com.petshop.api.model.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderResponse {
    private Long id;
    private LocalDateTime createdAt;
    private OrderStatus status;
    private BigDecimal total;
    private String notes;
    private Long customerId;
    private String customerName;
    private List<OrderItemResponse> items;

    public static OrderResponse from(Order order) {
        OrderResponse dto = new OrderResponse();
        dto.setId(order.getId());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setStatus(order.getStatus());
        dto.setTotal(order.getTotal());
        dto.setNotes(order.getNotes());
        if (order.getCostumer() != null) {
            dto.setCustomerId(order.getCostumer().getId());
            dto.setCustomerName(order.getCostumer().getName());
        }
        if (order.getItems() != null) {
            dto.setItems(order.getItems().stream()
                    .map(OrderItemResponse::from)
                    .collect(Collectors.toList()));
        }
        return dto;
    }
}