package com.example.receiptApp.service;

import com.example.receiptApp.dto.PurchaseItemRequest;
import com.example.receiptApp.dto.response.ReceiptResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    private String buildEmailBody(ReceiptResponse receiptResponse) {
        StringBuilder emailBody = new StringBuilder();

        emailBody.append("<html>")
                .append("<head>")
                .append("<style>")
                .append("body { font-family: 'Arial', sans-serif; background-color: #FFA500; padding: 20px; }")
                .append("h2 { color: #0066cc; }")
                .append("p { margin-bottom: 10px; }")
                .append("ul { list-style-type: none; padding: 0; }")
                .append("li { margin-bottom: 10px; }")
                .append(".separator { border-top: 2px solid #ccc; margin-top: 10px; }")
                .append(".section { background-color: #FFA500; padding: 10px; margin-bottom: 10px; }")
                .append("</style>")
                .append("</head>")
                .append("<body>")
                .append("<div class='section'>")
                .append("<h2>Receipt Details</h2>")
                .append("<div class='separator'></div>")
                .append("<p><strong>Receipt Number:</strong> ").append(receiptResponse.getReceiptNumber()).append("</p>")
                .append("<p><strong>Receipt Date:</strong> ").append(receiptResponse.getReceiptDate()).append("</p>")
                .append("<p><strong>Shop Name:</strong> ").append(receiptResponse.getShopName()).append("</p>")
                .append("<p><strong>Shop Address:</strong> ").append(receiptResponse.getShopAddress()).append("</p>")
                .append("<p><strong>Shop Email:</strong> ").append(receiptResponse.getShopEmail()).append("</p>")
                .append("<p><strong>Shop Phone Number:</strong> ").append(receiptResponse.getShopPhoneNumber()).append("</p>")
                .append("<p><strong>Customer Name:</strong> ").append(receiptResponse.getCustomerName()).append("</p>")
                .append("<p><strong>Customer Email:</strong> ").append(receiptResponse.getCustomerEmail()).append("</p>")
                .append("<p><strong>Customer Phone Number:</strong> ").append(receiptResponse.getCustomerPhoneNumber()).append("</p>")
                .append("<h3>Purchase Items</h3>")
                .append("<ul>");

        for (PurchaseItemRequest purchaseItem : receiptResponse.getPurchaseItemRequestList()) {
            emailBody.append("<li>")
                    .append("Item Name: ").append(purchaseItem.getItemName()).append("<br/>")
                    .append("Item Price: ").append(purchaseItem.getItemPrice()).append("<br/>")
                    .append("Item Quantity: ").append(purchaseItem.getItemQuantity())
                    .append("</li>");
        }
        emailBody.append("</ul>");
        emailBody.append("<p><strong> Amount:</strong> ").append(receiptResponse.getAmount()).append("</p>")
                .append("</div>")
                .append("</body>")
                .append("</html>");

        return emailBody.toString();

    }
    public void sendEmail(String to,
                          String subject,
                          String body){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // Set to true to indicate HTML content
        } catch (MessagingException e) {
            // Handle exception
            e.printStackTrace();
        }

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("oparadaniv@gmail.com");
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(body);
//
        javaMailSender.send(mimeMessage);
        System.out.println("Mail sent successfully");
    }

    public void sendReceiptEmail(ReceiptResponse response){
        String emailBody = buildEmailBody(response);

        // Send the email
        sendEmail(response.getCustomerEmail(), "Receipt", emailBody);
    }
}
