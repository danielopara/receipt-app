package com.example.receiptApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Data

public class PurchaseItemRequest {
    private String itemName;
    private BigDecimal itemPrice;
    private Integer itemQuantity;
}
