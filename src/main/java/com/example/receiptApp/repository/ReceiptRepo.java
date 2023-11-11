package com.example.receiptApp.repository;

import com.example.receiptApp.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReceiptRepo extends JpaRepository<Receipt, Long> {
    @Query(value = "SELECT * FROM receipt_table WHERE customer_email = :email", nativeQuery = true)
    List<Object[]> findAllColumnsByEmail(String email);

    @Query(value = "SELECT * FROM receipt_table WHERE customer_phone_number = :phoneNumber", nativeQuery = true)
    List<Object[]> findAllColumnsByPhoneNumber(String phoneNumber);

}
