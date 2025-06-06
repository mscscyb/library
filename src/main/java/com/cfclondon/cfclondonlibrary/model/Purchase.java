package com.cfclondon.cfclondonlibrary.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private int quantity;
    private String customerName;
    private double amountPaid;
    private String purchaseComment;
    private LocalDateTime purchaseDate;
    private double balanceRemaining;

    @PrePersist
    public void onCreate() {
        this.purchaseDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getPurchaseComment() {
        return purchaseComment;
    }

    public void setPurchaseComment(String purchaseComment) {
        this.purchaseComment = purchaseComment;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getBalanceRemaining() {
        return balanceRemaining;
    }

    public void setBalanceRemaining(double balanceRemaining) {
        this.balanceRemaining = balanceRemaining;
    }

    // Method to calculate the balance left (not stored in DB)
    public double getBalanceLeft() {
        if (this.book != null) {
            double totalAmount = this.quantity * this.book.getPrice();  // Assuming 'price' is in Book entity
            return totalAmount - this.amountPaid;
        }
        return 0;  // Return 0 if book is null (edge case)
    }
}
