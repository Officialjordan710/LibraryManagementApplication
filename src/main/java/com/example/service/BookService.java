package com.example.service;

import com.example.model.Book;
import com.example.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book markAsBorrowed(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        book.setAvailable(false);
        return bookRepository.save(book);
    }

    public Book markAsReturned(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        book.setAvailable(true);
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchBooks(String title, String author) {
        if (title != null && !title.isEmpty()) {
            return bookRepository.findByTitleContainingIgnoreCase(title);
        } else if (author != null && !author.isEmpty()) {
            return bookRepository.findByAuthorContainingIgnoreCase(author);
        } else {
            return bookRepository.findAll();
        }
    }
}

