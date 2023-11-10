package com.example.receiptApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "items_table")
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String itemName;
    public Integer itemQuantity;
    public BigDecimal itemPrice;
    @ManyToOne
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;
}
