package com.example.receiptApp.controller;

import com.example.receiptApp.dto.ReceiptRequest;
import com.example.receiptApp.dto.response.ReceiptResponse;
import com.example.receiptApp.execeptions.EmailExistsException;
import com.example.receiptApp.execeptions.ErrorResponse;
import com.example.receiptApp.model.Receipt;
import com.example.receiptApp.service.EmailService;
import com.example.receiptApp.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/receipt")
public class ReceiptController {
    @Autowired
    private ReceiptService receiptService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/create_receipt")
    public ResponseEntity<ReceiptResponse> createReceipt(@RequestBody ReceiptRequest request){
            ReceiptResponse response = receiptService.createReceipt(request);
            emailService.sendReceiptEmail(response);
            return ResponseEntity.ok(response);
    }

    @GetMapping("/email")
    public ResponseEntity<?> searchEmail(@RequestParam String email){
        try{
            List<Object[]> emailPresent = receiptService.findByEmail(email);
            if(!emailPresent.isEmpty()){
                return ResponseEntity.ok(emailPresent);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                        "User does not exist: " + email));
            }
        } catch (EmailExistsException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No user");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());

        }
    }
}
