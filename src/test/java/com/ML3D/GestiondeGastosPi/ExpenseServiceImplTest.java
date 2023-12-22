package com.ML3D.GestiondeGastosPi;

import com.ML3D.GestiondeGastosPi.dao.ExpenseRepository;
import com.ML3D.GestiondeGastosPi.dto.request.ExpenseRequestDto;
import com.ML3D.GestiondeGastosPi.dto.request.response.ExpenseCategoryResponseDto;
import com.ML3D.GestiondeGastosPi.dto.request.response.ExpenseResponseDto;
import com.ML3D.GestiondeGastosPi.entities.Expense;
import com.ML3D.GestiondeGastosPi.exception.DAOException;
import com.ML3D.GestiondeGastosPi.services.impl.ExpenseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateExpense() {
        ExpenseRequestDto requestDto = new ExpenseRequestDto();
        when(expenseRepository.insertExpense(any())).thenReturn(1);

        String response = expenseService.createExpense(requestDto);

        assertEquals("Gasto registrado", response);
        verify(expenseRepository, times(1)).insertExpense(any());
    }

    @Test
    void testUpdateExpense() {
        Long id = 1L;
        ExpenseRequestDto requestDto = new ExpenseRequestDto();
        when(expenseRepository.updateExpense(eq(id), any())).thenReturn(1);

        String response = expenseService.updateExpense(id, requestDto);

        assertEquals("Se actualizó el gasto con éxito", response);
        verify(expenseRepository, times(1)).updateExpense(eq(id), any());
    }

    @Test
    void testDeleteExpense() throws DAOException {
        Long id = 1L;
        doNothing().when(expenseRepository).deleteExpense(id);

        expenseService.deleteExpense(id);

        verify(expenseRepository, times(1)).deleteExpense(id);
    }

    @Test
    void testGetExpenseById() {
        Long id = 1L;
        Expense expense = new Expense();
        when(expenseRepository.selectExpenseById(id)).thenReturn(expense);

        ExpenseResponseDto responseDto = expenseService.getExpenseById(id);

        assertEquals(expenseService.mapExpenseToResponseDto(expense), responseDto);
        verify(expenseRepository, times(1)).selectExpenseById(id);
    }

    @Test
    void testGetAllExpenses() {
        List<Expense> expenses = Collections.singletonList(new Expense());
        when(expenseRepository.selectAllExpenses()).thenReturn(expenses);

        List<ExpenseResponseDto> responseDtos = expenseService.getAllExpenses();

        assertEquals(1, responseDtos.size());
        verify(expenseRepository, times(1)).selectAllExpenses();
    }
}

