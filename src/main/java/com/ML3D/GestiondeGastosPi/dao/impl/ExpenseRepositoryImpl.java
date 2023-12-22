package com.ML3D.GestiondeGastosPi.dao.impl;



import com.ML3D.GestiondeGastosPi.dao.ExpenseRepository;
import com.ML3D.GestiondeGastosPi.dao.dto.ExpenseDto;

import com.ML3D.GestiondeGastosPi.entities.Expense;
import com.ML3D.GestiondeGastosPi.entities.ExpenseCategory;
import com.ML3D.GestiondeGastosPi.exception.DAOException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ExpenseRepositoryImpl implements ExpenseRepository {


    private static final String INSERT_INTO_EXPENSE = "INSERT INTO Expense (amount, category_id, category_name, date) VALUES (?,?, ?, ?)";
    private static final String UPDATE_EXPENSE_BY_ID = "UPDATE Expense SET amount = ?, category_name = ?, date = ? WHERE id = ?";
    private static final String DELETE_EXPENSE = "DELETE FROM Expense WHERE id = ?";

    private static final String INSERT_INTO_CATEGORY_EXPENSE = "INSERT INTO ExpenseCategory (name) VALUES (?)";
    private static final String SELECT_FROM_EXPENSE_CATEGORY_BY_NAME = "SELECT * FROM ExpenseCategory WHERE name = ?";
    private static final String SELECT_ALL_EXPENSES = "SELECT * FROM Expense";
    private final JdbcTemplate jdbcTemplate;


    public ExpenseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer insertExpense(Expense expense) {
        jdbcTemplate.update(INSERT_INTO_CATEGORY_EXPENSE, expense.getCategoryName().toLowerCase());
        Object[] params = {expense.getCategoryName()};
        int[] types  = {1};

      ExpenseCategory expenseCategory = jdbcTemplate.queryForObject(
              SELECT_FROM_EXPENSE_CATEGORY_BY_NAME,
              params, types,
              new ExpenseCategoryRowMapper());
        assert expenseCategory != null;

      return jdbcTemplate.update(INSERT_INTO_EXPENSE,
                      expense.getAmount(), expenseCategory.getId(),
                      expenseCategory.getName(), expense.getDate());

        }

    @Override
    public List<Expense> selectAllExpenses() {
        return jdbcTemplate.query(SELECT_ALL_EXPENSES, new ExpenseRowMapper());
    }

    @Override
    public Integer updateExpense(Long id, Expense expense)  {
        System.out.println("Actualizando el gasto");
        return jdbcTemplate.update(UPDATE_EXPENSE_BY_ID,
                expense.getAmount(),
                expense.getCategoryName(),
                expense.getDate(),
                id);
    }

    @Override
    public void deleteExpense(Long id) throws DAOException {
        System.out.println("ID de Gasto eliminado : " + id);

        try {
            jdbcTemplate.update(DELETE_EXPENSE, id);
        } catch (DataAccessException exception) {
            throw new DAOException("No se pudo eliminar el gasto  " + id, exception);
        }
        System.out.println("Gasto eliminado ");
    }
    @Override
    public Expense selectExpenseById(Long id) {
        Object[] params = {id};
        int[] types = {1};

        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM Expense WHERE id = ?",
                    params, types,
                    new ExpenseRowMapper());
        } catch (DataAccessException e) {
            // Si no se encuentra ningún gasto, se puede manejar según tus necesidades
            return null;
        }
    }

    static class ExpenseCategoryRowMapper implements RowMapper <ExpenseCategory> {
        @Override
        public ExpenseCategory mapRow(ResultSet rs, int rowNum) throws SQLException{
            ExpenseCategory expenseCategory = new ExpenseCategory();
            expenseCategory.setId(rs.getLong("id"));
            expenseCategory.setName(rs.getString("name"));
            return expenseCategory;
        }
    }

        class ExpenseRowMapper implements RowMapper<Expense> {
            @Override
            public Expense mapRow(ResultSet rs, int rowNum) throws SQLException {
                Expense expense = new Expense();
                expense.setId((Long) rs.getLong("id"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setCategoryId((int) rs.getLong("category_id"));
                expense.setCategoryName(rs.getString("category_name"));
                expense.setDate(rs.getString("date"));
                return expense;
            }
        }

        }



