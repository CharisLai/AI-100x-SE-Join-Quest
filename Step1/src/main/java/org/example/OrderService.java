package org.example;

import java.util.*;

public class OrderService {
    public OrderSummary placeOrder(List<OrderItem> items) {
        // 無優惠
        return placeOrder(items, null, null);
    }
    public OrderSummary placeOrder(List<OrderItem> items, Integer threshold, Integer discount) {
        int originalAmount = 0;
        List<OrderItem> receivedItems = new ArrayList<>();
        for (OrderItem item : items) {
            originalAmount += item.getQuantity() * item.getUnitPrice();
            receivedItems.add(new OrderItem(item.getProductName(), item.getQuantity(), item.getUnitPrice(), item.getCategory()));
        }
        int appliedDiscount = 0;
        if (threshold != null && discount != null && originalAmount >= threshold) {
            appliedDiscount = discount;
        }
        int totalAmount = originalAmount - appliedDiscount;
        return new OrderSummary(originalAmount, appliedDiscount, totalAmount, receivedItems);
    }
    public OrderSummary placeOrderWithBogo(List<OrderItem> items) {
        int totalAmount = 0;
        List<OrderItem> receivedItems = new ArrayList<>();
        for (OrderItem item : items) {
            int receivedQty = item.getQuantity();
            if (item.getCategory() != null && item.getCategory().trim().equalsIgnoreCase("cosmetics")) {
                // 買N送1（買1送1=2，買2送1=3，買3送1=4，買4送2=6）
                receivedQty = item.getQuantity() + Math.max(1, item.getQuantity() / 2);
            }
            totalAmount += item.getQuantity() * item.getUnitPrice();
            receivedItems.add(new OrderItem(item.getProductName(), receivedQty, item.getUnitPrice(), item.getCategory()));
            System.out.println("下單: " + item.getProductName() + ", category=[" + item.getCategory() + "] bytes=" + (item.getCategory() == null ? "null" : java.util.Arrays.toString(item.getCategory().getBytes())) + ", 數量=" + item.getQuantity() + " => 實際收到=" + receivedQty);
        }
        return new OrderSummary(totalAmount, 0, totalAmount, receivedItems);
    }
    public OrderSummary placeOrderWithBogoAndThreshold(List<OrderItem> items, Integer threshold, Integer discount) {
        int originalAmount = 0;
        List<OrderItem> receivedItems = new ArrayList<>();
        for (OrderItem item : items) {
            originalAmount += item.getQuantity() * item.getUnitPrice();
            int receivedQty = item.getQuantity();
            if (item.getCategory() != null && item.getCategory().trim().equalsIgnoreCase("cosmetics")) {
                // 買N送1（買1送1=2，買2送1=3，買3送1=4，買4送2=6）
                receivedQty = item.getQuantity() + Math.max(1, item.getQuantity() / 2);
            }
            receivedItems.add(new OrderItem(item.getProductName(), receivedQty, item.getUnitPrice(), item.getCategory()));
            System.out.println("下單: " + item.getProductName() + ", category=[" + item.getCategory() + "] bytes=" + (item.getCategory() == null ? "null" : java.util.Arrays.toString(item.getCategory().getBytes())) + ", 數量=" + item.getQuantity() + " => 實際收到=" + receivedQty);
        }
        int appliedDiscount = 0;
        if (threshold != null && discount != null && originalAmount >= threshold) {
            appliedDiscount = discount;
        }
        int totalAmount = originalAmount - appliedDiscount;
        return new OrderSummary(originalAmount, appliedDiscount, totalAmount, receivedItems);
    }
    public OrderSummary placeOrder(List<OrderItem> items, Promotion promotion) {
        return promotion.apply(items);
    }
} 