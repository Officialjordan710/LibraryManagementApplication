package com.example.repository;

import com.example.model.BorrowRecord;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByUser(User user);
    List<BorrowRecord> findByReturnDateIsNull();
    List<BorrowRecord> findByReturnDateIsNullAndDueDateBefore(LocalDate date);
    Optional<BorrowRecord> findTopByBookIdAndReturnedFalseOrderByBorrowDateDesc(Long bookId);
}

