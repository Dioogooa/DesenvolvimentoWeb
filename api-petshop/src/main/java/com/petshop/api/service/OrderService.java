package com.petshop.api.service;

import com.petshop.api.dto.request.OrderItemRequest;
import com.petshop.api.dto.request.OrderRequest;
import com.petshop.api.dto.request.OrderStatusRequest;
import com.petshop.api.dto.response.OrderResponse;
import com.petshop.api.exception.BusinessException;
import com.petshop.api.exception.ResourceNotFoundException;
import com.petshop.api.model.*;
import com.petshop.api.repository.CategoryRepository;
import com.petshop.api.repository.CostumerRepository;
import com.petshop.api.repository.OrderRepository;
import com.petshop.api.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CostumerRepository costumerRepository;
    private final ProductRepository productRepository;

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Long id) {
        Order order = orderRepository.findByIdWithDeatails(id)
                .orElseThrow(()-> new ResourceNotFoundException("Pedido", id));

        return OrderResponse.from(order);
    }

    public List<OrderResponse> findByCostumer(Long costumerId) {
        return orderRepository.findByCostumerId(costumerId)
                .stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse create(OrderRequest request) {
        Costumer costumer = costumerRepository.findById(request.getCustomerId())
                .orElseThrow(()-> new ResourceNotFoundException("Cliente", request.getCustomerId()));

        Order order = Order.builder()
                .costumer(costumer)
                .notes(request.getNotes())
                .status(OrderStatus.PENDING)
                .items(new ArrayList<>())
                .build();

        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto", itemRequest.getProductId()));

            if (!product.getActive()) {
                throw new BusinessException("Produto indisponível: " + product.getName());
            }

            if (product.getStock() < itemRequest.getQuantity()) {
                throw new BusinessException("Estoque insuficiente para o produto: "  + product.getName()+ ". Disponivel: " +product.getStock());
            }

            OrdemItem item = OrdemItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(product.getPrice())
                    .build();

            order.getItems().add(item);

            product.setStock(product.getStock() - itemRequest.getQuantity());

            productRepository.save(product);
        }

        order.recalculateTotal();
        return OrderResponse.from(orderRepository.save(order));
    }

    @Transactional
    public OrderResponse update(Long id, OrderStatusRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Pedido", id));

        validadeStatusTransition(order.getStatus(), request.getStatus());
        order.setStatus(request.getStatus());
        return OrderResponse.from(orderRepository.save(order));
    }

    public void validadeStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        if (currentStatus == OrderStatus.CANCELLED) {
            throw new BusinessException("Não é possível alterar um pedido já entregue.");
        }

        if (newStatus == OrderStatus.PENDING && currentStatus != OrderStatus.PENDING) {
            throw new BusinessException("Não é possível voltar o pedido para PENDING");
        }
    }
}
