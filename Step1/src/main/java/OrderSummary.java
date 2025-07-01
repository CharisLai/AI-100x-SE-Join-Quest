import java.util.List;

public class OrderSummary {
    private int totalAmount;
    private List<OrderItem> receivedItems;

    public OrderSummary(int totalAmount, List<OrderItem> receivedItems) {
        this.totalAmount = totalAmount;
        this.receivedItems = receivedItems;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public List<OrderItem> getReceivedItems() {
        return receivedItems;
    }
} 