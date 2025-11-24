# AutomationExercise - Cart Module Tests

This repository contains my graduation project automation tests for the **Cart** module on [https://automationexercise.com](https://automationexercise.com).

## Technology Stack
- Java
- Selenium WebDriver
- TestNG
- Maven
- IntelliJ IDEA

## Project Structure
- `src/main/java/Cart`
  - `HomePage.java` – handles navigation to the Cart and Products.
  - `ProductsPage.java` – handles product listing and selecting a product.
  - `ProductDetailPage.java` – handles quantity input and Add to Cart action.
  - `CartPage.java` – handles cart verification and actions on items.
- `src/test/java/Zeka`
  - `BaseTests.java` – common setup and teardown.
  - `TestCart.java` – test cases for Cart scenarios.

## Test Scenarios
1. Add product to cart.
2. Change product quantity in cart.
3. Verify total price is updated correctly.
4. Remove product from cart.

## How to Run
1. Import the project into IntelliJ IDEA.
2. Run `mvn test` **or** run `TestCart` directly from the IDE.
