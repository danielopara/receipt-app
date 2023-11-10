package com.example.receiptApp.service;

import com.example.receiptApp.dto.PurchaseItemRequest;
import com.example.receiptApp.dto.ReceiptRequest;
import com.example.receiptApp.dto.response.ReceiptResponse;
import com.example.receiptApp.model.PurchaseItem;
import com.example.receiptApp.model.Receipt;
import com.example.receiptApp.repository.PurchaseItemRepo;
import com.example.receiptApp.repository.ReceiptRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReceiptService {
    @Autowired
    private ReceiptRepo receiptRepo;
    @Autowired
    private PurchaseItemRepo purchaseItemRepo;

    private static final Random RANDOM = new Random();
    private Set<Integer> generatedNumbers = new HashSet<>();

    private int generateUniqueRandomPart() {
        int randomInt;
        do {
            randomInt = RANDOM.nextInt(1000);
        } while (!generatedNumbers.add(randomInt));
        return randomInt;
    }
    public String generateReceiptNumber() {
        String currentDatePattern = "yyyyMMdd";
        int randomPart = generateUniqueRandomPart();
        String currentDate = new SimpleDateFormat(currentDatePattern).format(new Date());
        String sequentialNumber = String.format("%03d", randomPart);
        return currentDate + "-" + sequentialNumber;
    }

    public ReceiptResponse createReceipt(ReceiptRequest receiptRequest){
        Receipt receipt = new Receipt();
        BigDecimal totalAmount = BigDecimal.ZERO;

        List<PurchaseItem> purchaseItemRequests = new ArrayList<>();
        for(PurchaseItemRequest itemRequest : receiptRequest.getPurchaseItemRequest()){
            PurchaseItem purchaseItem = new PurchaseItem();
            purchaseItem.setItemName(itemRequest.getItemName());
            purchaseItem.setItemPrice(itemRequest.getItemPrice());
            purchaseItem.setItemQuantity(itemRequest.getItemQuantity());
            purchaseItem.setReceipt(receipt);
            purchaseItemRequests.add(purchaseItem);

            BigDecimal itemTotal = purchaseItem.getItemPrice().multiply(new BigDecimal(purchaseItem.getItemQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        receipt.setReceiptDate(new Date());
        receipt.setReceiptNumber(generateReceiptNumber());
        receipt.setAmount(totalAmount);
        receipt.setCustomerName(receiptRequest.getCustomerName());
        receipt.setCustomerEmail(receiptRequest.getCustomerEmail());
        receipt.setCustomerPhoneNumber(receiptRequest.getCustomerPhoneNumber());
        receipt.setShopEmail("shopWithUs@gmail.com");
        receipt.setShopName("Shop With Us");
        receipt.setShopPhoneNumber("07080574572");
        receipt.setShopAddress("Bogije");
        receipt.setPurchaseItems(purchaseItemRequests);
        receiptRepo.save(receipt);


        return new ReceiptResponse(receipt.getReceiptNumber(),
                receipt.getReceiptDate(),
                receipt.getShopName(),
                receipt.getShopAddress(),
                receipt.getShopEmail(),
                receipt.getShopPhoneNumber(),
                receipt.getCustomerName(),
                receipt.getCustomerEmail(),
                receipt.getCustomerPhoneNumber(),
                receipt.getAmount(),
                receiptRequest.getPurchaseItemRequest());
    }
}
