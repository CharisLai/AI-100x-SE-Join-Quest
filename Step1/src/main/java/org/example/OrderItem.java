package org.example;

public class OrderItem {
    private String productName;
    private int quantity;
    private int unitPrice;
    private String category;

    public OrderItem(String productName, int quantity, int unitPrice) {
        this(productName, quantity, unitPrice, null);
    }

    public OrderItem(String productName, int quantity, int unitPrice, String category) {
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public String getCategory() {
        return category;
    }
} 