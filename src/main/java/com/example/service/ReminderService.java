package com.example.service;

import com.example.model.BorrowRecord;
import com.example.repository.BorrowRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReminderService {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Scheduled(cron = "0 0 8 * * ?") // Every day at 8 AM
    public void sendOverdueReminders() {
        List<BorrowRecord> overdueRecords = borrowRecordRepository
                .findByReturnDateIsNullAndDueDateBefore(LocalDate.now());

        for (BorrowRecord record : overdueRecords) {
            String message = "Reminder: Book '" + record.getBook().getTitle() +
                    "' is overdue. Please return it.";
            System.out.println("Sending to " + record.getUser().getClass() + ": " + message);
        }
    }
}

