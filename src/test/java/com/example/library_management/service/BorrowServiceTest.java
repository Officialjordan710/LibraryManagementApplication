package com.example.library_management.service;

import com.example.model.Book;
import com.example.model.BorrowRecord;
import com.example.model.User;
import com.example.repository.BookRepository;
import com.example.repository.BorrowRecordRepository;
import com.example.repository.UserRepository;
import com.example.service.BorrowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowServiceTest {

    @InjectMocks
    private BorrowService borrowService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BorrowRecordRepository borrowRecordRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void borrowBook_Success() {
        Long bookId = 1L;
        Long userId = 2L;

        Book book = new Book();
        book.setId(bookId);
        book.setAvailable(true);

        User user = new User();
        user.setId(userId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenAnswer(i -> i.getArgument(0));
        when(bookRepository.save(any(Book.class))).thenAnswer(i -> i.getArgument(0));

        assertDoesNotThrow(() -> borrowService.borrowBook(bookId, userId));

        assertFalse(book.isAvailable());
        verify(borrowRecordRepository, times(1)).save(any(BorrowRecord.class));
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void borrowBook_BookNotAvailable_Throws() {
        Long bookId = 1L;
        Long userId = 2L;

        Book book = new Book();
        book.setId(bookId);
        book.setAvailable(false);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> borrowService.borrowBook(bookId, userId));

        assertEquals("Book is already borrowed", exception.getMessage());
    }

    @Test
    void returnBook_Success() {
        Long bookId = 1L;

        Book book = new Book();
        book.setId(bookId);
        book.setAvailable(false);

        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setReturned(false);

        when(borrowRecordRepository.findTopByBookIdAndReturnedFalseOrderByBorrowDateDesc(bookId))
                .thenReturn(Optional.of(record));
        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenAnswer(i -> i.getArgument(0));
        when(bookRepository.save(any(Book.class))).thenAnswer(i -> i.getArgument(0));

        assertDoesNotThrow(() -> borrowService.returnBook(bookId));

        assertTrue(record.isReturned());
        assertNotNull(record.getReturnDate());
        assertTrue(book.isAvailable());

        verify(borrowRecordRepository, times(1)).save(record);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void searchBooks_ReturnsBooks() {
        String title = "Java";
        String author = "Author";

        List<Book> expectedBooks = List.of(new Book(), new Book());

        when(bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title, author))
                .thenReturn(expectedBooks);

        List<Book> actualBooks = borrowService.searchBooks(title, author);

        assertEquals(expectedBooks.size(), actualBooks.size());
        verify(bookRepository, times(1)).findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCase(title, author);
    }
}
