package com.example.controller;

import com.example.model.Book;
import com.example.service.BookService;
import com.example.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    @Autowired
    private BorrowService BorrowService;

    @PostMapping("/{bookId}/user/{userId}")
    public ResponseEntity<?> borrowBook(@PathVariable Long bookId, @PathVariable Long userId) {
        BorrowService.borrowBook(bookId, userId);
        return ResponseEntity.ok("Book borrowed successfully");
    }

    @PostMapping("/{bookId}/return")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId) {
        BorrowService.returnBook(bookId);
        return ResponseEntity.ok("Book returned successfully");
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam(required = false) String title,
                                  @RequestParam(required = false) String author) {
        return BorrowService.searchBooks(title, author);
    }
}

