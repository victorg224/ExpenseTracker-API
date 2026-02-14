package com.example.expensetracker;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue
    private Integer id;
    private String category;
    private String description;
    private BigDecimal amount;
    private LocalDate date;

    public Expense(String category, BigDecimal amount, LocalDate date){
        if(category==null){
            throw new IllegalArgumentException("Category needs to be entered");
        }
        if(amount==null){
            throw new IllegalArgumentException("Amount needs to be entered");
        }
        if(date==null){
            throw new IllegalArgumentException("Date needs to be entered");
        }

        this.category = category;
        this.amount = amount;
        this.date = date;
    }
}
