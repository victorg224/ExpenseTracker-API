package com.example.expensetracker;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor

public class ExpenseService {

    private final ExpenseRepository expenseRepository;


    private List<Expense> getExpenses(){
        return expenseRepository.findAll();
    }


    public ExpenseResponseDTO addExpense(CreateExpenseDTO request){

        Expense expense = new Expense();

        expense.setDescription(request.getDescription());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());
        Expense saved = expenseRepository.save(expense);

        return toResponseDTO(saved);

    }

    public ExpenseResponseDTO updateExpense(Integer id, UpdateExpenseDTO request){
       Expense expense = expenseRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Expense not found"));

        if(request.getDescription() !=null){
            expense.setDescription(request.getDescription());
        }

        if(request.getAmount() !=null){
            expense.setAmount(request.getAmount());
        }

        if(request.getCategory() !=null){
            expense.setCategory(request.getCategory());
        }

        if(request.getDate() !=null){
            expense.setDate(request.getDate());
        }

        Expense updated = expenseRepository.save(expense);
        return toResponseDTO(updated);
    }

    public void deleteExpense(Integer id) {
        if(!expenseRepository.existsById(id)){
            throw new RuntimeException("Expense doesn't exist");
        }
        expenseRepository.deleteById(id);
    }

    public ExpenseResponseDTO toResponseDTO(Expense expense){
        ExpenseResponseDTO response = new ExpenseResponseDTO();
        response.setId(expense.getId());
        response.setDescription(expense.getDescription());
        response.setAmount(expense.getAmount());
        response.setDate(expense.getDate());

        return response;

    }

    public BigDecimal getTotalExpense(){
       return expenseRepository.findAll().stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public Map<String, BigDecimal> getTotalByCategory(){
        Map<String, BigDecimal> catTotals = new HashMap<>();
        for (Expense e : getExpenses()) {
            catTotals.merge(
                    e.getCategory(),
                    e.getAmount(),
                    BigDecimal::add
            );
        }
        return catTotals;

    }

    public String getHighestSpending(){
        return getTotalByCategory()
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    //return lowest
    public String getLowestSpending() {
        return getTotalByCategory()
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    public TreeMap<String, BigDecimal> getExpenseTrend() {
        TreeMap<String, BigDecimal> trend = new TreeMap<>(); // TreeMap sorts by date

        for (Expense e : getExpenses()) {
            trend.merge(
                    String.valueOf(e.getDate()),
                    e.getAmount(),
                    BigDecimal::add
            );
        }
        return trend;
    }

}
