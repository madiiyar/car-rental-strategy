# Car Rental: Strategy Pattern (Assignment 5)

A small Java 21 / Maven project with two Strategy families for a car rental: **pricing** and **insurance**. Each family has an interface, three strategies, a context, and a selector. Pricing also reads outside data (fleet utilization) through an interface of its own.

## How to run

- Run all tests: `mvn test`
- Run the demo: `mvn -q compile`, then `java -cp target/classes com.rental.Main`

The demo shows three things: the same request through every strategy, manual choice versus automatic choice by the selector, and switching the strategy at runtime.

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

## Selector rules (first match wins)

| Selector | Order of rules |
|---|---|
| InsuranceSelector | 1. Driver younger than 25: YoungDriverInsurance. 2. Car value 30000 or more: FullCoverageInsurance. 3. Otherwise: BasicInsurance. |
| PricingSelector | 1. Fleet utilization above 0.8: DemandPricing. 2. Seven days or more: LongTermPricing. 3. Otherwise: StandardPricing. |

Only the two selectors contain `if` statements that choose a strategy. The contexts only delegate.

## Isolation from change

`DemandPricing` needs the current fleet utilization. It depends only on the `FleetUtilizationProvider` interface, never on a concrete source.

- The program uses `SimulatedFleetUtilizationProvider`, a value the demo can change.
- The tests use `FakeFleetUtilizationProvider`, which returns fixed numbers.
- A real source (database, file, web service) is one new class that implements the interface. `DemandPricing` and the contexts stay untouched.

## Why Strategy, not State or Template Method

Both families do one job in several interchangeable ways: price a rental, price the insurance. A client or a selector picks one algorithm from outside, and a context runs it through a common interface. That is the Strategy pattern.

It is not State. In State the object itself changes its behavior when its internal state changes, and the states usually move the object to the next state. A rental here has no life cycle. A strategy never replaces itself and never knows the other strategies. Something outside, the client or the selector, decides which one runs.

It is not Template Method. Template Method uses inheritance: a base class fixes the skeleton of one algorithm, subclasses override single steps, and the choice is made when the object is created. Our algorithms share no skeleton (a flat fee, a percentage of the car value, age brackets), so there are no steps to override. Each strategy replaces the whole algorithm, and the context swaps it at runtime with `setStrategy()`, using composition instead of inheritance.

## Tests

JUnit 5. Every strategy has its own test class, both selectors have tests for every rule including the boundary values, and each family has one `@ParameterizedTest` that checks "the cost is never negative" for all strategies of that family. Contexts are tested with a lambda strategy and a runtime swap. Pricing tests use the fake data source.

## Refactoring history

The insurance family was written first the "bad" way (one class with a `switch`), then refactored to Strategy. The same test class `InsuranceCostTest` passes on both commits. Only the small test helper `InsuranceTestSupport` changed.

- Before (one class with `switch`): https://github.com/madiiyar/car-rental-strategy/tree/before-strategy
- After (Strategy pattern): https://github.com/madiiyar/car-rental-strategy/tree/after-strategy
- Difference between them: https://github.com/madiiyar/car-rental-strategy/compare/before-strategy...after-strategy