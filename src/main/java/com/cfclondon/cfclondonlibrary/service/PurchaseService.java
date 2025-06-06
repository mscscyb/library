package com.cfclondon.cfclondonlibrary.service;

import com.cfclondon.cfclondonlibrary.model.Book;
import com.cfclondon.cfclondonlibrary.model.Purchase;
import com.cfclondon.cfclondonlibrary.repository.BookRepository;
import com.cfclondon.cfclondonlibrary.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private BookRepository bookRepository;

    public void purchaseBook(Long bookId, int quantity, String comment, String customerName, double amountPaid) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null || book.getStock() < quantity) {
            return;
        }

        book.setStock(book.getStock() - quantity);

        book.setDateOfPurchase(LocalDateTime.now());

        // Append the comment (only if it's not blank)
        if (comment != null && !comment.trim().isEmpty()) {
            String existingComment = book.getComment() == null ? "" : book.getComment();
            String updatedComment = existingComment.isBlank()
                    ? comment.trim()
                    : existingComment + " | " + comment.trim();
            book.setComment(updatedComment);
        }

        bookRepository.save(book);

        Purchase purchase = new Purchase();
        purchase.setBook(book);
        purchase.setQuantity(quantity);
        purchase.setPurchaseComment(comment);
        purchase.setCustomerName(customerName); // Set customer name
        purchase.setAmountPaid(amountPaid); // Set amount paid
        purchaseRepository.save(purchase); // Save the purchase

    }

    private double calculateBalanceRemaining(Book book, int quantity, double amountPaid) {
        double totalPrice = book.getPrice() * quantity; // Total price of the books
        return totalPrice - amountPaid; // Balance remaining
    }

    public Purchase getPurchaseById(Long id) {
        return purchaseRepository.findById(id).orElse(null);
    }

    public void updatePurchaseComment(Long id, String newComment) {
        Purchase purchase = getPurchaseById(id);
        if (purchase != null) {
            purchase.setPurchaseComment(newComment);
            purchaseRepository.save(purchase);
        }
    }

    // Fetch all purchases for a specific book
    public List<Purchase> getPurchasesForBook(Book book) {
        return purchaseRepository.findByBook(book);
    }

    // Calculate the total number of books sold for a given book
    public int calculateTotalBooksSold(Book book) {
        List<Purchase> purchases = getPurchasesForBook(book);
        return purchases.stream().mapToInt(Purchase::getQuantity).sum();
    }

    // Calculate total revenue for a given book (total books sold * price of the book)
    public double calculateTotalRevenue(Book book) {
        List<Purchase> purchases = getPurchasesForBook(book);
        return purchases.stream().mapToDouble(purchase -> purchase.getQuantity() * book.getPrice()).sum();
    }
}
