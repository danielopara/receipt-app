package com.example.receiptApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "receipt_table")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String receiptNumber;
    public Date receiptDate;
    public String shopName;
    public String shopAddress;
    public String shopEmail;
    public String shopPhoneNumber;
    public String customerName;
    public String customerEmail;
    public String customerPhoneNumber;
    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL)
    public List<PurchaseItem> purchaseItems;
    public BigDecimal amount;
}
