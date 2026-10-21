package com.rental.pricing;

import com.rental.model.Car;
import com.rental.model.RentalRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DemandPricingTest {

    private final RentalRequest request = new RentalRequest(new Car("Toyota Camry", 50.0, 20000.0), 3, 30);

    private double priceAt(double utilization) {
        return new DemandPricing(new FakeFleetUtilizationProvider(utilization)).calculate(request);
    }

    @Test
    void emptyFleetGivesTheStandardPrice() {
        assertEquals(150.0, priceAt(0.0), 0.001);
    }

    @Test
    void halfFullFleetAddsTwentyFivePercent() {
        assertEquals(187.5, priceAt(0.5), 0.001);
    }

    @Test
    void completelyFullFleetAddsFiftyPercent() {
        assertEquals(225.0, priceAt(1.0), 0.001);
    }

    @Test
    void utilizationAboveOneIsClampedToOne() {
        assertEquals(225.0, priceAt(2.0), 0.001);
    }

    @Test
    void negativeUtilizationIsClampedToZero() {
        assertEquals(150.0, priceAt(-1.0), 0.001);
    }
}