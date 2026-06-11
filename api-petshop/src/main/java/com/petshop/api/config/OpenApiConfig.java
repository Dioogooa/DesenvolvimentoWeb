package com.petshop.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI petShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pet Shop API")
                        .description("""
                                API REST para e-commerce de produtos para pets.
                                
                                Permite gerenciar categorias, produtos, clientes e pedidos.
                                
                                **Fluxo de status do pedido:**
                                `PENDING` → `CONFIRMED` → `SHIPPED` → `DELIVERED`
                                
                                Qualquer status (exceto DELIVERED) pode ser alterado para `CANCELLED`.
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Pet Shop")
                                .email("contato@petshop.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Ambiente local")
                ));
    }
}