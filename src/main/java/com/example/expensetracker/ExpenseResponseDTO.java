package com.example.expensetracker;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class ExpenseResponseDTO {
    @Schema(description = "id", example = "01", required= true)
    private Integer id;

    @Schema(description = "Expense description", example= "Coffee", required = true)
    private String description;

    @Schema(description = "Expense Amount", example = "$7.50", minimum = "0")
    private BigDecimal amount;

    @Schema(description = "Expense category", example = "Food", required = true)
    private String category;

    @Schema(description = "Expense date", example = "2026-02-12", format = "date")
    private LocalDate date;

}
