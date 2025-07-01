import java.util.*;

public class OrderService {
    public OrderSummary placeOrder(List<OrderItem> items) {
        int totalAmount = 0;
        List<OrderItem> receivedItems = new ArrayList<>();
        for (OrderItem item : items) {
            totalAmount += item.getQuantity() * item.getUnitPrice();
            receivedItems.add(new OrderItem(item.getProductName(), item.getQuantity(), item.getUnitPrice()));
        }
        return new OrderSummary(totalAmount, receivedItems);
    }
} 