package com.rental.insurance;

import com.rental.model.Car;
import com.rental.model.RentalRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicInsuranceTest {

    private final InsuranceStrategy strategy = new BasicInsurance();
    private final Car car = new Car("Toyota Camry", 50.0, 20000.0);

    @Test
    void chargesFiveForOneDay() {
        assertEquals(5.0, strategy.calculate(new RentalRequest(car, 1, 30)), 0.001);
    }

    @Test
    void costGrowsLinearlyWithDays() {
        assertEquals(50.0, strategy.calculate(new RentalRequest(car, 10, 30)), 0.001);
    }

    @Test
    void ignoresCarValue() {
        Car luxury = new Car("BMW 7", 150.0, 60000.0);
        assertEquals(15.0, strategy.calculate(new RentalRequest(luxury, 3, 30)), 0.001);
    }
}