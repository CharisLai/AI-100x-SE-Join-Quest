package org.example;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.datatable.DataTable;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.cucumber.java.Before;

public class OrderStepDefs {
    private List<org.example.OrderItem> orderItems;
    private org.example.OrderSummary orderSummary;
    private org.example.OrderService orderService = new org.example.OrderService();
    private Integer threshold = null;
    private Integer discount = null;
    private boolean bogoCosmeticsActive = false;

    @Before
    public void resetPromotions() {
        threshold = null;
        discount = null;
        bogoCosmeticsActive = false;
    }

    @Given("no promotions are applied")
    public void no_promotions_are_applied() {
        threshold = null;
        discount = null;
        bogoCosmeticsActive = false;
    }

    @Given("the threshold discount promotion is configured:")
    public void the_threshold_discount_promotion_is_configured(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> config = rows.get(0);
        threshold = Integer.parseInt(config.get("threshold"));
        discount = Integer.parseInt(config.get("discount"));
    }

    @Given("the buy one get one promotion for cosmetics is active")
    public void the_bogo_promotion_for_cosmetics_is_active() {
        bogoCosmeticsActive = true;
    }

    @When("a customer places an order with:")
    public void a_customer_places_an_order_with(DataTable dataTable) {
        System.out.println("bogoCosmeticsActive=" + bogoCosmeticsActive + ", threshold=" + threshold + ", discount=" + discount);
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        orderItems = new ArrayList<>();
        for (Map<String, String> row : rows) {
            String productName = null;
            String quantityStr = null;
            String unitPriceStr = null;
            String category = null;
            for (String key : row.keySet()) {
                String trimmedKey = key.trim().toLowerCase();
                if (trimmedKey.equals("productname")) {
                    productName = row.get(key);
                } else if (trimmedKey.equals("quantity")) {
                    quantityStr = row.get(key);
                } else if (trimmedKey.equals("unitprice")) {
                    unitPriceStr = row.get(key);
                } else if (trimmedKey.equals("category")) {
                    category = row.get(key);
                }
            }
            int quantity = Integer.parseInt(quantityStr);
            int unitPrice = Integer.parseInt(unitPriceStr);
            orderItems.add(new org.example.OrderItem(productName, quantity, unitPrice, category));
        }
        if (bogoCosmeticsActive && threshold != null && discount != null) {
            orderSummary = orderService.placeOrderWithBogoAndThreshold(orderItems, threshold, discount);
        } else if (bogoCosmeticsActive) {
            orderSummary = orderService.placeOrderWithBogo(orderItems);
        } else if (threshold != null && discount != null) {
            orderSummary = orderService.placeOrder(orderItems, threshold, discount);
        } else {
            orderSummary = orderService.placeOrder(orderItems);
        }
    }

    @Then("the order summary should be:")
    public void the_order_summary_should_be(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> expected = rows.get(0);
        if (expected.containsKey("totalAmount")) {
            assertEquals(Integer.parseInt(expected.get("totalAmount")), orderSummary.getTotalAmount());
        }
        if (expected.containsKey("originalAmount")) {
            assertEquals(Integer.parseInt(expected.get("originalAmount")), orderSummary.getOriginalAmount());
        }
        if (expected.containsKey("discount")) {
            assertEquals(Integer.parseInt(expected.get("discount")), orderSummary.getDiscount());
        }
    }

    @And("the customer should receive:")
    public void the_customer_should_receive(DataTable dataTable) {
        List<Map<String, String>> expectedRows = dataTable.asMaps(String.class, String.class);
        List<org.example.OrderItem> received = orderSummary.getReceivedItems();
        assertEquals(expectedRows.size(), received.size());
        for (int i = 0; i < expectedRows.size(); i++) {
            assertEquals(expectedRows.get(i).get("productName"), received.get(i).getProductName());
            assertEquals(Integer.parseInt(expectedRows.get(i).get("quantity")), received.get(i).getQuantity());
        }
    }
} 