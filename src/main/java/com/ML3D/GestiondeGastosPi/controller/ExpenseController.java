package com.ML3D.GestiondeGastosPi.controller;



import com.ML3D.GestiondeGastosPi.dto.request.ExpenseRequestDto;
import com.ML3D.GestiondeGastosPi.dto.request.response.ExpenseResponseDto;
import com.ML3D.GestiondeGastosPi.exception.DAOException;
import com.ML3D.GestiondeGastosPi.services.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }


    @PostMapping()
    public ResponseEntity<String> createExpenseHandler(@RequestBody ExpenseRequestDto expenseRequestDto)  {
        String response = expenseService.createExpense(expenseRequestDto);
        System.out.println("ExpenseController: creando un gasto");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


   @PutMapping("/update")
    public ResponseEntity<String> updateExpense(@RequestParam Long id,
                                                @RequestBody ExpenseRequestDto expenseRequestDto) {
        String response = expenseService.updateExpense(id, expenseRequestDto);
        System.out.println("ExpenseController: actualizando el gasto");
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) throws DAOException {
        expenseService.deleteExpense(id);
        System.out.println("ExpenseController: eliminando el gasto");
        return ResponseEntity
                .status(HttpStatus.GONE)
                .body("Se eliminó el gasto con id: " + id);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long id) {
        ExpenseResponseDto expenseResponseDto = expenseService.getExpenseById(id);
        if (expenseResponseDto != null) {
            System.out.println("ExpenseController: obteniendo el gasto con id: " + id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(expenseResponseDto);
        } else {
            System.out.println("ExpenseController: no se encontró el gasto con id: " + id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el gasto con id: " + id);
        }
    }



    @GetMapping()
    public ResponseEntity<List<ExpenseResponseDto>> getExpenses() {
        List<ExpenseResponseDto> responses = expenseService.getAllExpenses();
        System.out.println("ExpenseController: obteniendo todos los gastos");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responses);
    }
}