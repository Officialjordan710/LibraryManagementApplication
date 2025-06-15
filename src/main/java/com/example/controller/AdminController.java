package com.example.controller;

import com.example.model.BorrowRecord;
import com.example.repository.BorrowRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @GetMapping("/overdue")
    public List<BorrowRecord> getOverdueBooks() {
        return borrowRecordRepository.findByReturnDateIsNullAndDueDateBefore(LocalDate.now());
    }
}

