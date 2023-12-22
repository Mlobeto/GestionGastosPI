package com.ML3D.GestiondeGastosPi.dao;



import com.ML3D.GestiondeGastosPi.dao.dto.ExpenseDto;
import com.ML3D.GestiondeGastosPi.entities.Expense;
import com.ML3D.GestiondeGastosPi.exception.DAOException;

import java.util.List;

public interface ExpenseRepository {
    Integer insertExpense(Expense expense);
    Integer updateExpense(Long id, Expense expense);
    void deleteExpense(Long id) throws DAOException;
    Expense selectExpenseById(Long id);
    List<Expense> selectAllExpenses();
}
