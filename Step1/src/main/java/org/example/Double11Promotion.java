package org.example;
import java.util.*;

public class Double11Promotion implements Promotion {
    @Override
    public OrderSummary apply(List<OrderItem> items) {
        int originalAmount = 0;
        int discount = 0;
        int totalAmount = 0;
        List<OrderItem> receivedItems = new ArrayList<>();
        for (OrderItem item : items) {
            int qty = item.getQuantity();
            int unitPrice = item.getUnitPrice();
            int discounted = (qty / 10) * 10;
            int undiscounted = qty % 10;
            int itemOriginal = qty * unitPrice;
            int itemDiscount = (int)(discounted * unitPrice * 0.2);
            int itemTotal = (int)(discounted * unitPrice * 0.8) + undiscounted * unitPrice;
            originalAmount += itemOriginal;
            discount += itemDiscount;
            totalAmount += itemTotal;
            receivedItems.add(new OrderItem(item.getProductName(), qty, unitPrice, item.getCategory()));
        }
        return new OrderSummary(originalAmount, discount, totalAmount, receivedItems);
    }
} 