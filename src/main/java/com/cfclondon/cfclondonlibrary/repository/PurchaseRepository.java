package com.cfclondon.cfclondonlibrary.repository;

import com.cfclondon.cfclondonlibrary.model.Book;
import com.cfclondon.cfclondonlibrary.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByBookId(Long bookId);

    List<Purchase> findByBook(Book book);
}

