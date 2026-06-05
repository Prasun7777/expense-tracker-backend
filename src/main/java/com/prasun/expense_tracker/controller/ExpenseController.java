package com.prasun.expense_tracker.controller;

import com.prasun.expense_tracker.entity.Expense;
import com.prasun.expense_tracker.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@CrossOrigin(origins = "https://your-vercel-app.vercel.app")
@RestController
@RequestMapping("/expenses")
@Tag(name = "Expense API",
        description = "Expense Management APIs")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @PostMapping
    public Expense addExpense(
            @Valid @RequestBody Expense expense) {

        return service.addExpense(expense);
    }

    @Operation(
            summary = "Get all expenses",
            description = "Returns all expenses for logged-in user"
    )
    @GetMapping
    public List<Expense> getAllExpenses() {
        return service.getAllExpenses();
    }

    @GetMapping("/{id}")
    public Expense getExpenseById(
            @PathVariable Long id) {

        return service.getExpenseById(id);
    }

    @GetMapping("/category/{category}")
    public List<Expense> getExpensesByCategory(
            @PathVariable String category) {

        return service.getExpensesByCategory(category);
    }

    @PutMapping("/{id}")
    public Expense updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody Expense expense) {

        return service.updateExpense(id, expense);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(
            @PathVariable Long id) {

        service.deleteExpense(id);

        return "Expense deleted successfully";
    }
}