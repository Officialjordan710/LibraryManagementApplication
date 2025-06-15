package com.example.service;

import com.example.model.Book;
import com.example.model.BorrowRecord;
import com.example.model.User;
import com.example.repository.BookRepository;
import com.example.repository.BorrowRecordRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    public void borrowBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (!book.isAvailable()) {
            throw new RuntimeException("Book is already borrowed");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setUser(user);
        record.setBorrowDate(LocalDate.now());
        record.setReturned(false);

        book.setAvailable(false);

        borrowRecordRepository.save(record);
        bookRepository.save(book);
    }

    public void returnBook(Long bookId) {
        BorrowRecord record = borrowRecordRepository
                .findTopByBookIdAndReturnedFalseOrderByBorrowDateDesc(bookId)
                .orElseThrow(() -> new RuntimeException("No active borrow record found"));

        record.setReturnDate(LocalDate.now());
        record.setReturned(true);

        Book book = record.getBook();
        book.setAvailable(true);

        borrowRecordRepository.save(record);
        bookRepository.save(book);
    }

    public List<Book> searchBooks(String title, String author) {
        return bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title, author);
    }
}
