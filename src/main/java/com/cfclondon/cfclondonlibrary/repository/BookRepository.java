package com.cfclondon.cfclondonlibrary.repository;

import com.cfclondon.cfclondonlibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByOrderByDateOfPurchaseDesc();
}