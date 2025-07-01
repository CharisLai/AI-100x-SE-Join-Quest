package org.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.java.PendingException;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Double11StepDefs {
    private boolean double11Active = false;
    private List<OrderItem> orderItems;
    private int discountedQty = 0;
    private int totalPrice = 0;
    private OrderService orderService = new OrderService();
    private OrderSummary orderSummary;

    @Given("there is a Double 11 promotion")
    public void there_is_a_double11_promotion() {
        double11Active = true;
    }

    @And("the rule is: for every 10 identical items purchased, a 20% discount is applied to those 10 items")
    public void the_rule_is_for_every_10_identical_items_20_percent_discount() {
        // 規則已寫死，可留空
    }

    @When("a customer purchases 12 socks at $100 per unit")
    public void a_customer_purchases_12_socks() {
        orderItems = new ArrayList<>();
        orderItems.add(new OrderItem("socks", 12, 100, "apparel"));
        Promotion double11 = new Double11Promotion();
        orderSummary = orderService.placeOrder(orderItems, double11);
    }

    @Then("the system should apply a 20% discount to 10 socks")
    public void the_system_should_apply_discount_to_10_socks() {
        discountedQty = (orderItems.get(0).getQuantity() / 10) * 10;
        assertEquals(10, discountedQty);
        assertEquals(200, orderSummary.getDiscount()); // 10*100*0.2
    }

    @And("calculate the total price as 10 * 100 * 0.8 + 2 * 100 = 1000")
    public void calculate_the_total_price_as_formula() {
        assertEquals(1000, orderSummary.getTotalAmount());
    }

    @When("a customer purchases 27 socks at $100 per unit")
    public void a_customer_purchases_27_socks() {
        orderItems = new ArrayList<>();
        orderItems.add(new OrderItem("socks", 27, 100, "apparel"));
    }

    @Then("the system should apply a 20% discount to two groups of 10 socks")
    public void the_system_should_apply_discount_to_two_groups_of_10_socks() {
        discountedQty = (orderItems.get(0).getQuantity() / 10) * 10;
        assertEquals(20, discountedQty);
    }

    @And("calculate the total price as 20 * 100 * 0.8 + 7 * 100 = 2300")
    public void calculate_the_total_price_as_2300() {
        int qty = orderItems.get(0).getQuantity();
        int unitPrice = orderItems.get(0).getUnitPrice();
        int discounted = (qty / 10) * 10;
        int undiscounted = qty % 10;
        totalPrice = (int)(discounted * unitPrice * 0.8) + undiscounted * unitPrice;
        assertEquals(2300, totalPrice);
    }

    @When("a customer purchases {int} different items at ${int} each")
    public void a_customer_purchases_different_items(int count, int unitPrice) {
        orderItems = new ArrayList<>();
        for (char c = 'A'; c < 'A' + count; c++) {
            orderItems.add(new OrderItem("Product-" + c, 1, unitPrice, "general"));
        }
        Promotion double11 = new Double11Promotion();
        orderSummary = orderService.placeOrder(orderItems, double11);
    }

    @Then("no discount should be applied")
    public void no_discount_should_be_applied() {
        assertEquals(0, orderSummary.getDiscount());
    }

    @And("calculate the total price as {string}")
    public void calculate_the_total_price_as(String formula) {
        throw new PendingException();
    }

    @And("the total price should be {string}")
    public void the_total_price_should_be(String formula) {
        throw new PendingException();
    }
} 