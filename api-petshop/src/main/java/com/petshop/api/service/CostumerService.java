package com.petshop.api.service;

import com.petshop.api.dto.request.CostumerRequest;
import com.petshop.api.dto.response.CustomerResponse;
import com.petshop.api.exception.BusinessException;
import com.petshop.api.exception.ResourceNotFoundException;
import com.petshop.api.model.Costumer;
import com.petshop.api.repository.CostumerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CostumerService {

    private final CostumerRepository costumerRepository;

    public List<CustomerResponse> findAll() {
        return costumerRepository.findAll()
                .stream()
                .map(CustomerResponse::from)
                .collect(Collectors.toList());
    }

    public CustomerResponse findById(long id) {
        return CustomerResponse.from(costumerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Cliente", id)));
    }

    @Transactional
    public CustomerResponse create(CostumerRequest request) {
        if (costumerRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Já existe um cliente com o email: " + request.getEmail());
        }

        if (costumerRepository.existsByCpf(request.getCpf())) {
            throw new BusinessException("Já existe um cliente com o cpf: " + request.getCpf());
        }

        Costumer costumer = Costumer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .cpf(request.getCpf())
                .phone(request.getPhone())
                .address(request.getPhone())
                .build();

        return CustomerResponse.from(costumerRepository.save(costumer));
    }

    @Transactional
    public CustomerResponse update(Long id, CostumerRequest request) {
        Costumer costumer = costumerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Cliente", id));

        costumerRepository.findByEmail(request.getEmail())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id))
                        throw new BusinessException("Email já cadastrado para outro cliente.");
                });

        costumerRepository.findByCpf(request.getCpf())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id))
                        throw new BusinessException("CPF já cadastrado para outro cliente.");
                });

        costumer.setName(request.getName());
        costumer.setEmail(request.getEmail());
        costumer.setCpf(request.getCpf());
        costumer.setPhone(request.getPhone());
        costumer.setAddress(request.getAddress());

        return CustomerResponse.from(costumerRepository.save(costumer));
    }

    @Transactional
    public void delete(long id) {
        Costumer costumer = costumerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Cliente", id));

        costumerRepository.delete(costumer);
    }
}
