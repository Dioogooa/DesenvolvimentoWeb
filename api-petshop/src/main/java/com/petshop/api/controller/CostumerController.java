package com.petshop.api.controller;

import com.petshop.api.dto.request.CategoryRequest;
import com.petshop.api.dto.request.CostumerRequest;
import com.petshop.api.dto.response.CategoryResponse;
import com.petshop.api.dto.response.CustomerResponse;
import com.petshop.api.service.CostumerService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/costumers")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Gerenciamento de clientes")
public class CostumerController {

    private final CostumerService costumerService;

    @GetMapping
    @Operation(summary = "Listar todas os clientes")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<CustomerResponse>> getCategories() {
        return ResponseEntity.ok(costumerService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<CustomerResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(costumerService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos"),
            @ApiResponse(responseCode = "422", description = "E-mail ou CPF já cadastrado")
    })
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CostumerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(costumerService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cleiente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente atualizado"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "422", description = "E-mail ou CPF já cadastrado")
    })
    public ResponseEntity<CustomerResponse> update(@PathVariable Long id, @Valid @RequestBody CostumerRequest request) {
        return ResponseEntity.ok(costumerService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente exluido"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        costumerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
