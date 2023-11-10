package com.example.receiptApp.controller;

import com.example.receiptApp.dto.ReceiptRequest;
import com.example.receiptApp.dto.response.ReceiptResponse;
import com.example.receiptApp.model.Receipt;
import com.example.receiptApp.service.EmailService;
import com.example.receiptApp.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/receipt")
public class ReceiptController {
    @Autowired
    private ReceiptService receiptService;
    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<ReceiptResponse> createReceipt(@RequestBody ReceiptRequest request){
            ReceiptResponse response = receiptService.createReceipt(request);
            emailService.sendReceiptEmail(response);
            return ResponseEntity.ok(response);
    }
}
