package com.ML3D.GestiondeGastosPi.dao.dto;

public class ExpenseCategoryDto {
    private String name;

    public ExpenseCategoryDto() {
    }

    public ExpenseCategoryDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
