package com.example.receiptApp.repository;

import com.example.receiptApp.model.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemRepo extends JpaRepository<PurchaseItem, Long> {
}
