package com.rental.pricing;

import com.rental.model.Car;
import com.rental.model.RentalRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LongTermPricingTest {

    private final PricingStrategy strategy = new LongTermPricing();
    private final Car car = new Car("Toyota Camry", 50.0, 20000.0);

    private double price(int days) {
        return strategy.calculate(new RentalRequest(car, days, 30));
    }

    @Test
    void sixDaysHaveNoDiscount() {
        assertEquals(300.0, price(6), 0.001);
    }

    @Test
    void seventhDayIsDiscountedByFifteenPercent() {
        assertEquals(342.5, price(7), 0.001);
    }

    @Test
    void tenDaysMixFullAndDiscountedDays() {
        assertEquals(470.0, price(10), 0.001);
    }

    @Test
    void twentyNinthDayIsStillInTheFirstDiscountTier() {
        assertEquals(1277.5, price(29), 0.001);
    }

    @Test
    void thirtiethDayStartsTheDeepDiscount() {
        assertEquals(1312.5, price(30), 0.001);
    }

    @Test
    void longRentalIsCheaperThanStandardPricing() {
        double standard = new StandardPricing().calculate(new RentalRequest(car, 40, 30));
        assertTrue(price(40) < standard);
    }
}