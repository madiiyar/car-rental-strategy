package com.rental.insurance;

import com.rental.model.Car;
import com.rental.model.RentalRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FullCoverageInsuranceTest {

    private final InsuranceStrategy strategy = new FullCoverageInsurance();

    @Test
    void chargesOnePercentOfCarValuePlusDailyFee() {
        Car car = new Car("Toyota Camry", 50.0, 20000.0);
        assertEquals(209.0, strategy.calculate(new RentalRequest(car, 3, 30)), 0.001);
    }

    @Test
    void expensiveCarCostsMore() {
        Car luxury = new Car("BMW 7", 150.0, 60000.0);
        assertEquals(606.0, strategy.calculate(new RentalRequest(luxury, 2, 30)), 0.001);
    }

    @Test
    void driverAgeDoesNotChangeTheCost() {
        Car car = new Car("Toyota Camry", 50.0, 20000.0);
        double young = strategy.calculate(new RentalRequest(car, 3, 19));
        double older = strategy.calculate(new RentalRequest(car, 3, 50));
        assertEquals(young, older, 0.001);
    }
}