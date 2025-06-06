package com.cfclondon.cfclondonlibrary.service;

import com.cfclondon.cfclondonlibrary.model.Book;
import com.cfclondon.cfclondonlibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAllByOrderByDateOfPurchaseDesc();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public void saveBook(Book book) {
        bookRepository.save(book);
    }

//    public void deleteBook(Long id) {
//        bookRepository.deleteById(id);
//    }

    public boolean purchaseBook(Long bookId, int quantity) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            if (book.getStock() >= quantity) {
                book.setStock(book.getStock() - quantity);
                bookRepository.save(book);
                return true;
            }
        }
        return false;
    }
}
