package com.example.expensetracker;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.*;

@RestController
@RequestMapping("api/expense")
@AllArgsConstructor
@Tag(name = "Expense Management", description = "API's for managing expense data")
public class ExpenseController {

    private ExpenseService expenseService;

    @GetMapping("/getExpenses")
    @Operation(
            summary = "Get total expenses",
            description = "Calculates and returns the sum of all expenses"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successful")
    })
    public BigDecimal getExpenseTotal(){
       return expenseService.getTotalExpense();
    }

    @GetMapping("/by-category")
    @Operation(
            summary = "Get total expenses by category",
            description = "Filters and calculates total expenses by category"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successful")
    })
    public Map<String,BigDecimal> getTotalByCategory(){
        return expenseService.getTotalByCategory();
    }

    @GetMapping("/highest-spending")
    @Operation(
            summary = "Get highest spending category",
            description = "Returns category with highest spending"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successful")
    })
    public String getHighestSpending(){
        return expenseService.getHighestSpending();

    }

    @GetMapping("/getLowest")
    @Operation(
            summary = "Get lowest spending category",
            description = "Returns category with lowest spending"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successful")
    })
    public String getLowestSpending(){
        return expenseService.getLowestSpending();
    }

    @GetMapping("/trend")
    @Operation(
            summary = "Get expense trends",
            description = "Returns expense over time"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successful")
    })
    public TreeMap<String,BigDecimal> getExpenseTrend(){

        return expenseService.getExpenseTrend();
    }

    //CRUD endpoints

    @PostMapping
    @Operation(
            summary = "Create new expense",
            description = "Creates new expense in record"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successfully added expense"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ExpenseResponseDTO addExpense( @Parameter(description = "Expense object", required = true) @RequestBody  CreateExpenseDTO request){
        return expenseService.addExpense(request);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update expense",
            description = "Updates expense record with given id"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successfully added expense"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    public ExpenseResponseDTO updateExpense( @Parameter(description = "ID of expense to update", required = true, example="1") @PathVariable Integer id, @RequestBody UpdateExpenseDTO expense){
      return expenseService.updateExpense(id, expense);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete expense",
            description = "Deletes expense with given ID"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successfully added expense"),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    public void deleteExpense( @Parameter(description = "ID of expense to delete", required = true, example="1") @PathVariable Integer id){

        expenseService.deleteExpense(id);
    }
}
