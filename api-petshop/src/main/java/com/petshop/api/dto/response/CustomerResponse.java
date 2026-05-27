package com.petshop.api.dto.response;

import com.petshop.api.model.Costumer;
import com.petshop.api.model.Costumer;
import lombok.Data;

@Data
public class CustomerResponse {
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String phone;
    private String address;

    public static CustomerResponse from(Costumer costumer) {
        CustomerResponse dto = new CustomerResponse();
        dto.setId(costumer.getId());
        dto.setName(costumer.getName());
        dto.setEmail(costumer.getEmail());
        dto.setCpf(costumer.getCpf());
        dto.setPhone(costumer.getPhone());
        dto.setAddress(costumer.getAddress());
        return dto;
    }
}