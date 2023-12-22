package com.ML3D.GestiondeGastosPi.services.impl;

import com.ML3D.GestiondeGastosPi.dao.ExpenseRepository;
import com.ML3D.GestiondeGastosPi.dao.dto.ExpenseDto;
import com.ML3D.GestiondeGastosPi.dto.request.ExpenseRequestDto;
import com.ML3D.GestiondeGastosPi.dto.request.response.ExpenseCategoryResponseDto;
import com.ML3D.GestiondeGastosPi.dto.request.response.ExpenseResponseDto;
import com.ML3D.GestiondeGastosPi.entities.Expense;
import com.ML3D.GestiondeGastosPi.exception.DAOException;
import com.ML3D.GestiondeGastosPi.services.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public String createExpense(ExpenseRequestDto expenseRequestDto)  {
        String response = "Gasto registrado";

        Expense expense = mapDtoToExpense(expenseRequestDto);

        Integer responseInserted = expenseRepository.insertExpense(expense);
        if(responseInserted.equals(0)){
            System.out.println("No se registró el Gasto");
        }

        return response;
    }

    @Override
    public String updateExpense(Long id, ExpenseRequestDto expenseRequestDto) {

        String response = "Gasto Actualizado";
        Expense expense = mapDtoToExpense(expenseRequestDto);
        Integer responsesUpdated = expenseRepository.updateExpense(id, expense);

        if (responsesUpdated.equals(0)) {
            System.out.println("No se actualizó ningún registro con el id " + id);
        }
        System.out.println("Se actualiza la presentacion id: " + id);
        return response;
    }

    @Override
    public void deleteExpense(Long id) throws DAOException {
        expenseRepository.deleteExpense(id);
    }

    @Override
    public ExpenseResponseDto getExpenseById(Long id) {
        Expense expense = expenseRepository.selectExpenseById(id);

        return mapExpenseToResponseDto(expense);
    }

    private Expense mapDtoToExpense(ExpenseRequestDto expenseRequestDto) {
        Expense expense = new Expense();
        if (expenseRequestDto.getId() != null) {
            expense.setId(expenseRequestDto.getId());
        }
        expense.setAmount(expenseRequestDto.getAmount());
        expense.setCategoryName(expenseRequestDto.getCategoryRequestDto().getName());
        expense.setDate(expenseRequestDto.getDate());
        return expense;
    }


    public ExpenseResponseDto mapExpenseToResponseDto(Expense expense) {
        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();
        expenseResponseDto.setAmount(expense.getAmount());

        ExpenseCategoryResponseDto categoryDto = new ExpenseCategoryResponseDto();
        categoryDto.setId(expense.getId());
        categoryDto.setName(expense.getCategoryName());

        expenseResponseDto.setCategoryDto(categoryDto);
        expenseResponseDto.setDate(expense.getDate());
        return expenseResponseDto;
    }
    @Override
    public List<ExpenseResponseDto> getAllExpenses() {
        List<Expense> expenses = expenseRepository.selectAllExpenses();
        return expenses.stream().map(this::mapExpenseToResponseDto).collect(Collectors.toList());
    }


}

