package com.ML3D.GestiondeGastosPi.dto.request.response;

public class ExpenseResponseDto {
    private Double amount;
    private ExpenseCategoryResponseDto categoryDto;
    private String date;

    public ExpenseResponseDto() {

    }

    public ExpenseResponseDto(Double amount, ExpenseCategoryResponseDto categoryDto, String date) {
        this.amount = amount;
        this.categoryDto = categoryDto;
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ExpenseCategoryResponseDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(ExpenseCategoryResponseDto categoryDto) {
        this.categoryDto = categoryDto;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ExpenseResponseDto{" +
                "amount=" + amount +
                ", categoryDto=" + (categoryDto != null ? categoryDto.toString() : "null") +
                ", date='" + date + '\'' +
                '}';
    }
}
