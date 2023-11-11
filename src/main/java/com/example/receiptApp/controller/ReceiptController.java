package com.example.receiptApp.controller;

import com.example.receiptApp.dto.ReceiptRequest;
import com.example.receiptApp.dto.response.ReceiptResponse;
import com.example.receiptApp.execeptions.EmailExistsException;
import com.example.receiptApp.execeptions.ErrorResponse;
import com.example.receiptApp.execeptions.PhoneNumberExistsException;
import com.example.receiptApp.model.Receipt;
import com.example.receiptApp.service.EmailService;
import com.example.receiptApp.service.ReceiptService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/v1/receipt")
public class ReceiptController {
    @Autowired
    private ReceiptService receiptService;
    @Autowired
    private EmailService emailService;
//    private static final Logger logger = LoggerFactory.getLogger(ReceiptController.class);


    @PostMapping("/create_receipt")
    public ResponseEntity<ReceiptResponse> createReceipt(@RequestBody ReceiptRequest request){
            ReceiptResponse response = receiptService.createReceipt(request);
//            emailService.sendReceiptEmail(response);

        if (response.getCustomerEmail() != null && !response.getCustomerEmail().isEmpty()) {
            emailService.sendReceiptEmail(response);
        } else {
            // Log a warning or handle the case where the email is empty or null
            System.out.println("No email");
        }
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
                        "Customer email does not exist: " + email));
            }
        } catch (EmailExistsException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No user");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());
        }
    }
    @GetMapping("/phone-number")
    public ResponseEntity<?> searchPhoneNumber(@RequestParam String phone_number){
        try{
            List<Object[]> phoneNumberPresent = receiptService.findByPhoneNumber(phone_number);
            if(phoneNumberPresent.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                                "Customer phone number does not exists: "+ phone_number));
            }else{
                return ResponseEntity.ok(phoneNumberPresent);
            }
        } catch (PhoneNumberExistsException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred: " + e.getMessage());
        }
    }
}
