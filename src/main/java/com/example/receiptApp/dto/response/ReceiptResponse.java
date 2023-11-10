package com.example.receiptApp.dto.response;

import com.example.receiptApp.dto.PurchaseItemRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReceiptResponse {
    private String receiptNumber;
    private Date receiptDate;
    private String shopName;
    private String shopAddress;
    private String shopEmail;
    private String shopPhoneNumber;
    private String customerName;
    private String customerEmail;
    private String customerPhoneNumber;
    private BigDecimal amount;
    private List<PurchaseItemRequest> purchaseItemRequestList;
}
