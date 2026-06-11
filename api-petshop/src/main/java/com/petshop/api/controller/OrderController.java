package com.petshop.api.controller;

import com.petshop.api.dto.request.OrderRequest;
import com.petshop.api.dto.request.OrderStatusRequest;
import com.petshop.api.dto.request.ProductRequest;
import com.petshop.api.dto.response.OrderResponse;
import com.petshop.api.dto.response.ProductResponse;
import com.petshop.api.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Gerenciamento de pedidos")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "Listar todos os pedidos", description = "Use ID para filtrar um cliente especifico")
    public ResponseEntity<List<OrderResponse>> findAll(@Parameter(description = "Filtrar por ID do cliente")
                                                           @RequestParam(required = false) Long costumerId) {

        if (costumerId == null) {
            return ResponseEntity.ok(orderService.findByCostumer(costumerId));
        }
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Criar novo pedido")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos"),
            @ApiResponse(responseCode = "404", description = "Cliente ou produto não encontrado"),
            @ApiResponse(responseCode = "422", description = "Estoque insuficiente ou produto inativo")
    })
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do pedido", description = "FLUXO ENUM: PENDING - SHIPPED - DELIVERED ou CANCELLED")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Status atualizado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "422", description = "Transição de status inválida"),

    })
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable Long id, @Valid @RequestBody OrderStatusRequest request) {
        return ResponseEntity.ok(orderService.updateStatus(id, request));
    }
}
