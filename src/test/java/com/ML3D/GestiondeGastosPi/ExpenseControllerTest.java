package com.ML3D.GestiondeGastosPi;

import com.ML3D.GestiondeGastosPi.controller.ExpenseController;
import com.ML3D.GestiondeGastosPi.dto.request.ExpenseRequestDto;
import com.ML3D.GestiondeGastosPi.dto.request.response.ExpenseResponseDto;
import com.ML3D.GestiondeGastosPi.exception.DAOException;
import com.ML3D.GestiondeGastosPi.services.ExpenseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ExpenseControllerTest {

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private ExpenseController expenseController;

    @Test
    void testCreateExpenseHandler() {
        // Given
        ExpenseRequestDto expenseRequestDto = new ExpenseRequestDto();
        Mockito.when(expenseService.createExpense(Mockito.any(ExpenseRequestDto.class))).thenReturn("Gasto registrado");

        // When
        ResponseEntity<String> result = expenseController.createExpenseHandler(expenseRequestDto);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("Gasto registrado", result.getBody());
    }

    @Test
    void testUpdateExpense() {
        // Given
        Long id = 1L;
        ExpenseRequestDto expenseRequestDto = new ExpenseRequestDto();
        Mockito.when(expenseService.updateExpense(Mockito.eq(id), Mockito.any(ExpenseRequestDto.class))).thenReturn("Se actualizó el gasto con éxito");

        // When
        ResponseEntity<String> result = expenseController.updateExpense(id, expenseRequestDto);

        // Then
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
        assertEquals("Se actualizó el gasto con éxito", result.getBody());
    }

    @Test
    void testDeleteExpense() throws DAOException {
        // Given
        Long id = 1L;
        Mockito.doNothing().when(expenseService).deleteExpense(Mockito.eq(id));

        // When
        ResponseEntity<String> result = expenseController.deleteExpense(id);

        // Then
        assertEquals(HttpStatus.GONE, result.getStatusCode());
        assertEquals("Se eliminó el gasto con id: 1", result.getBody());
    }

    @Test
    void testGetExpenseById() {
        // Given
        Long id = 1L;
        ExpenseResponseDto expenseResponseDto = new ExpenseResponseDto();
        Mockito.when(expenseService.getExpenseById(Mockito.eq(id))).thenReturn(expenseResponseDto);

        // When
        ResponseEntity<?> result = expenseController.getExpenseById(id);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(expenseResponseDto, result.getBody());
    }

    @Test
    void testGetExpenses() {
        // Given
        List<ExpenseResponseDto> responses = Collections.singletonList(new ExpenseResponseDto());
        Mockito.when(expenseService.getAllExpenses()).thenReturn(responses);

        // When
        ResponseEntity<List<ExpenseResponseDto>> result = expenseController.getExpenses();

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responses, result.getBody());
    }
}

