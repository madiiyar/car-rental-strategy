# Car Rental: Strategy Pattern (Assignment 5)

Two strategy families for a car rental: **pricing** and **insurance**.

## How to run
- Tests: `mvn test`
- Demo: `mvn -q compile` then `java -cp target/classes com.rental.Main`

## Class responsibilities

| Class | Responsibility |
|---|---|
| Car | Holds the model name, daily rate, market value of one rentable car. |
| RentalRequest | Holds the car, the number of days, the driver age of one rental. |
| InsuranceType | Names the three insurance kinds for manual choice in tests. |
| InsuranceStrategy | Declares the one method every insurance cost algorithm must offer. |
| BasicInsurance | Charges a flat fee for each rental day. |
| FullCoverageInsurance | Charges one percent of the car value plus a flat fee for each day. |
| YoungDriverInsurance | Charges the basic fee plus a daily surcharge that depends on the driver age bracket. |
| InsuranceContext | Holds the current insurance strategy, lets clients replace it, delegates the cost calculation to it. |
| InsuranceSelector | Chooses the insurance strategy matching the driver age, then the car value. |
| PricingStrategy | Declares the one method every rental price algorithm must offer. |
| StandardPricing | Multiplies the number of days by the daily rate of the car. |
| LongTermPricing | Applies lower daily rates to days beyond the sixth, even lower beyond the twenty-ninth. |
| DemandPricing | Raises the standard price in proportion to the current fleet utilization. |
| PricingContext | Holds the current pricing strategy, lets clients replace it, delegates the price calculation to it. |
| PricingSelector | Chooses the pricing strategy from fleet utilization, then rental length. |
| FleetUtilizationProvider | Hides the source of the current share of rented cars behind one method. |
| SimulatedFleetUtilizationProvider | Returns a utilization value that the demo can change on demand. |
| Main | Wires the objects together, then prints the demo for three scenarios. |

## Why Strategy, not State or Template Method

(write this in your own words, see the notes below)

## Refactoring history
- Before (one class with `switch`): <link to the "before" commit>
- After (Strategy pattern): <link to the "after" commit>