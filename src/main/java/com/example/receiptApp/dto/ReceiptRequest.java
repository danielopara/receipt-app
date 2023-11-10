package com.example.receiptApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReceiptRequest {
    private String customerName;
    private String customerEmail;
    private String customerPhoneNumber;

    private List<PurchaseItemRequest> purchaseItemRequest;
}
