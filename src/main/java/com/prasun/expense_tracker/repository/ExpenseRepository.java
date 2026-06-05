package com.prasun.expense_tracker.repository;

import com.prasun.expense_tracker.entity.Expense;
import com.prasun.expense_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.prasun.expense_tracker.entity.User;
import java.util.List;

public interface ExpenseRepository
        extends JpaRepository<Expense, Long> {

    List<Expense> findByCategory(String category);
    List<Expense> findByUser(User user);
}