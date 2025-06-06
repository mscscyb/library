package com.cfclondon.cfclondonlibrary.controller;

import com.cfclondon.cfclondonlibrary.model.Book;
import com.cfclondon.cfclondonlibrary.model.Purchase;
import com.cfclondon.cfclondonlibrary.service.BookService;
import com.cfclondon.cfclondonlibrary.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/books";
    }

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "bookList";
    }

    @GetMapping("/new")
    public String createBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "bookForm";
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String viewBookDetails(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));

        model.addAttribute("book", book);
        model.addAttribute("purchases", purchaseService.getPurchasesForBook(book));
        return "bookdetail";
    }

    @PostMapping("/purchase/{id}")
    public String purchaseBook(@PathVariable Long id,
                               @RequestParam int quantity,
                               @RequestParam String customerName,
                               @RequestParam double amountPaid,
                               @RequestParam(required = false) String comment) {

        purchaseService.purchaseBook(id, quantity, comment, customerName, amountPaid);
        return "redirect:/books";
    }

    @PostMapping("/updateComment/{id}")
    @ResponseBody
    public String updateComment(@PathVariable Long id, @RequestBody Book bookUpdate) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID"));
        book.setComment(bookUpdate.getComment());
        bookService.saveBook(book);
        return "success";
    }

//    // Optional: Delete book
//    @PostMapping("/delete/{id}")
//    public String deleteBook(@PathVariable Long id) {
//        bookService.deleteBook(id);
//        return "redirect:/books";
//    }
}
