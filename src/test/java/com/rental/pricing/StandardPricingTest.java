package com.rental.pricing;

import com.rental.model.Car;
import com.rental.model.RentalRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StandardPricingTest {

    private final PricingStrategy strategy = new StandardPricing();
    private final Car car = new Car("Toyota Camry", 50.0, 20000.0);

    @Test
    void oneDayCostsTheDailyRate() {
        assertEquals(50.0, strategy.calculate(new RentalRequest(car, 1, 30)), 0.001);
    }

    @Test
    void threeDaysCostThreeTimesTheRate() {
        assertEquals(150.0, strategy.calculate(new RentalRequest(car, 3, 30)), 0.001);
    }

    @Test
    void noDiscountEvenForLongRentals() {
        assertEquals(500.0, strategy.calculate(new RentalRequest(car, 10, 30)), 0.001);
    }
}