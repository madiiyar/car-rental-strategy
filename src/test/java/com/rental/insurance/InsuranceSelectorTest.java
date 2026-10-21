package com.rental.insurance;

import com.rental.model.Car;
import com.rental.model.RentalRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class InsuranceSelectorTest {

    private final InsuranceSelector selector = new InsuranceSelector();

    private static final Car CHEAP = new Car("Toyota Camry", 50.0, 20000.0);
    private static final Car LUXURY = new Car("BMW 7", 150.0, 60000.0);

    @Test
    void driverUnder25GetsYoungDriverInsurance() {
        assertInstanceOf(YoungDriverInsurance.class, selector.select(new RentalRequest(CHEAP, 3, 24)));
    }

    @Test
    void driver25WithCheapCarGetsBasicInsurance() {
        assertInstanceOf(BasicInsurance.class, selector.select(new RentalRequest(CHEAP, 3, 25)));
    }

    @Test
    void carWorthExactly30000GetsFullCoverage() {
        Car edge = new Car("Edge", 100.0, 30000.0);
        assertInstanceOf(FullCoverageInsurance.class, selector.select(new RentalRequest(edge, 3, 40)));
    }

    @Test
    void carJustBelow30000GetsBasicInsurance() {
        Car almost = new Car("Almost", 100.0, 29999.99);
        assertInstanceOf(BasicInsurance.class, selector.select(new RentalRequest(almost, 3, 40)));
    }

    @Test
    void youngDriverRuleWinsOverExpensiveCarRule() {
        assertInstanceOf(YoungDriverInsurance.class, selector.select(new RentalRequest(LUXURY, 3, 20)));
    }
}