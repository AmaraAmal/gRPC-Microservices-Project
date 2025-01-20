package com.project.gRPC.entity;

public class productEntity {
    private Integer id;
    private String name;
    private String description;
    private Float price;
    private Integer stock_quantity;

    public productEntity(String name, String description, Float price, Integer stock_quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock_quantity = stock_quantity;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getStock_quantity() {
        return stock_quantity;
    }
}
