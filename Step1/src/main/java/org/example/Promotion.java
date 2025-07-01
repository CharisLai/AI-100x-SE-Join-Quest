package org.example;
import java.util.List;

public interface Promotion {
    OrderSummary apply(List<OrderItem> items);
} 