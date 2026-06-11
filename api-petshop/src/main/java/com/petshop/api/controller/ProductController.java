package com.petshop.api.controller;


import com.petshop.api.dto.request.ProductRequest;
import com.petshop.api.dto.response.ProductResponse;
import com.petshop.api.service.ProductService;
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
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Gerenciamento de produtos do pet Shop")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Listar produtos ativos", description = "Retorna todos os produtos ativos")
    public ResponseEntity<List<ProductResponse>> findAll(
            @Parameter(description = "Busca por nome do produto")
            @RequestParam(required = false) String search,
            @Parameter(description = "Filtrar por ID da categoria")
            @RequestParam(required = false) Long categoryId ) {

        if (search != null && !search.isEmpty()) {
            return ResponseEntity.ok(productService.search(search));
        }
        if (categoryId != null) {
            return ResponseEntity.ok(productService.findByCategory(categoryId));
        }
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo produto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados invalidos"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto")
    @ApiResponses ({
            @ApiResponse(responseCode = "200", description = "ProdutoAtualizado"),
            @ApiResponse(responseCode = "400", description = "Produto ou categoria não encontada")
    })
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar produto, não remove, apenas desativa o produto.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto desativado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
