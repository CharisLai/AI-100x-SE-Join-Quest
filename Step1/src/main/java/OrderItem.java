public class OrderItem {
    private String productName;
    private int quantity;
    private int unitPrice;

    public OrderItem(String productName, int quantity, int unitPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
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
} 