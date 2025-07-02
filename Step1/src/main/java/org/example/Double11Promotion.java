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
            int itemOriginal = item.getQuantity() * item.getUnitPrice();
            int itemDiscount = calculateDiscount(item);
            int itemTotal = itemOriginal - itemDiscount;
            originalAmount += itemOriginal;
            discount += itemDiscount;
            totalAmount += itemTotal;
            receivedItems.add(new OrderItem(item.getProductName(), item.getQuantity(), item.getUnitPrice(), item.getCategory()));
        }
        return new OrderSummary(originalAmount, discount, totalAmount, receivedItems);
    }

    // 私有方法：計算單一商品的折扣
    private int calculateDiscount(OrderItem item) {
        int discounted = (item.getQuantity() / 10) * 10;
        return (int)(discounted * item.getUnitPrice() * 0.2);
    }
} 