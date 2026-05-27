package com.petshop.api.repository;

import com.petshop.api.model.OrdemItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdemItemRepository extends JpaRepository<OrdemItem, Long> {
    List<OrdemItem> findByOrderId(long orderId);
}
