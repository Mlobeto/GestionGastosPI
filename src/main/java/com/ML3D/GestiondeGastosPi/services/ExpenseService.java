package com.ML3D.GestiondeGastosPi.services;

import com.ML3D.GestiondeGastosPi.dto.request.ExpenseRequestDto;
import com.ML3D.GestiondeGastosPi.dto.request.response.ExpenseResponseDto;
import com.ML3D.GestiondeGastosPi.exception.DAOException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExpenseService {

    String createExpense(ExpenseRequestDto expenseRequestDto) ;

    String updateExpense(Long id, ExpenseRequestDto expenseRequestDto);
    void deleteExpense(Long id) throws DAOException ;
    ExpenseResponseDto getExpenseById(Long id);

    List<ExpenseResponseDto> getAllExpenses();
}
