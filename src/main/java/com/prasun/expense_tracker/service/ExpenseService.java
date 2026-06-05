package com.prasun.expense_tracker.service;

import com.prasun.expense_tracker.entity.Expense;
import com.prasun.expense_tracker.repository.ExpenseRepository;
import com.prasun.expense_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.prasun.expense_tracker.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;
    private final UserRepository userRepository;

    public ExpenseService(
            ExpenseRepository repository,
            UserRepository userRepository) {

        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Expense addExpense(
            Expense expense) {

        User currentUser = getCurrentUser();

        expense.setUser(currentUser);

        return repository.save(expense);
    }

    public List<Expense> getAllExpenses() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return repository.findByUser(user);
    }

    public List<Expense> getExpensesByCategory(
            String category) {

        return repository.findByCategory(category);
    }

    public Expense getExpenseById(Long id) {

        Expense expense = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Expense not found"));

        User currentUser = getCurrentUser();

        if (!expense.getUser().getId()
                .equals(currentUser.getId())) {

            throw new RuntimeException(
                    "Access denied");
        }

        return expense;
    }
    public Expense updateExpense(Long id,
                                 Expense updatedExpense) {

        Expense expense = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Expense not found"));

        User currentUser = getCurrentUser();

        if (!expense.getUser().getId()
                .equals(currentUser.getId())) {

            throw new RuntimeException(
                    "Access denied");
        }

        expense.setTitle(updatedExpense.getTitle());
        expense.setAmount(updatedExpense.getAmount());
        expense.setCategory(updatedExpense.getCategory());
        expense.setExpenseDate(updatedExpense.getExpenseDate());

        return repository.save(expense);
    }
    public void deleteExpense(Long id) {

        Expense expense = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Expense not found"));

        User currentUser = getCurrentUser();

        if (!expense.getUser().getId()
                .equals(currentUser.getId())) {

            throw new RuntimeException(
                    "Access denied");
        }

        repository.delete(expense);
    }
    private User getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }
}