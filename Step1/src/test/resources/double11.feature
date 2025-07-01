Feature: Double 11 Discount Promotion

  Background:
    Given there is a Double 11 promotion
    And the rule is: for every 10 identical items purchased, a 20% discount is applied to those 10 items

  # 第一個 scenario 已通過
  # Scenario: Buying 12 socks priced at $100 each
  #   When a customer purchases 12 socks at $100 per unit
  #   Then the system should apply a 20% discount to 10 socks
  #   And calculate the total price as 10 * 100 * 0.8 + 2 * 100 = 1000

  # 第二個 scenario 已通過
  # Scenario: Buying 27 socks priced at $100 each
  #   When a customer purchases 27 socks at $100 per unit
  #   Then the system should apply a 20% discount to two groups of 10 socks
  #   And calculate the total price as 20 * 100 * 0.8 + 7 * 100 = 2300

  Scenario: Buying 1 unit each of 10 different products (A to J), each priced at $100
    When a customer purchases 10 different items at $100 each
    Then no discount should be applied
    And the total price should be 10 * 100 = 1000