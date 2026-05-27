package com.petshop.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "costumer")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder

public class Costumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank (message = "Nome obrigatório!")
    @Size(min = 3, max = 150)
    @Column(nullable = false, length = 150)
    private String name;

    @NotBlank (message = "Email obrigatório!")
    @Email(message = "Email invalido")
    @Column(nullable = false, unique = true ,length = 150)
    private String email;

    @NotBlank (message = "Cpf obrigatório!")
    @Pattern(regexp = "\\d{11}", message = "Cpf deve conter 11 digitos numericos")
    @Column(nullable = false,unique = true ,length = 11)
    private String cpf;

    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 digios")
    @Column(length = 11)
    private String phone;

    @Size(max = 255, message = "Endereço deve ter no maximo 255 caracteres")
    @Column(length = 255)
    private String address;

    @OneToMany(mappedBy = "costumer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
}
