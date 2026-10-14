# Lab 2 CSD230 W26: Building a Transactional Bookstore with Spring MVC

## Overview
- Creating and persisting OrderEntity
- Implementing a Many‑to‑Many relationship between Order ↔ Product
- Calculating the total purchase amount
- Decrementing inventory stock (copies)
- Clearing the shopping cart after checkout
- Displaying an Order Confirmation page
- Demonstrating the full transactional flow in the browser

## UML Diagram

![img.png](UML%20Diagram.png)

## OrderEntity (JPA Evidence)

![img_1.png](OrderEntity%20(JPA%20Evidence).png)

## Project Structure

![img_2.png](Project%20Structure.png)

## Shopping Cart (cartDetails.html)

![img_3.png](Shopping%20Cart%20(cartDetails.html).png)

## Order Confirmation (orderDetails.html)

![img_4.png](Order%20Confirmation%20(orderDetails.html).png)

## Inventory Update (bookList.html)

![img_5.png](Inventory%20Update%20(bookList.html).png)

## Application Configuration

![img_6.png](Application%20Configuration.png)

# Functional Testing Summary

- Home — Book List (The Hobbit Copies: 10)

![img.png](Book%20List.png)

- Add — User adds a book to the cart

![img_1.png](Add%20to%20cart.png) 

- Checkout >> Confirmation — Order Confirmation page

![img_3.png](Checkout.png) 

- Verification — Post‑checkout behavior (Copies changed from 10 → 9)

![img_2.png](Post‑checkout.png) 


## Test Successful
- test confirms that the Cart ↔ Product Many‑to‑Many relationship is correctly implemented and functioning as expected.

![img.png](CartProductRelationshipTest.png)



