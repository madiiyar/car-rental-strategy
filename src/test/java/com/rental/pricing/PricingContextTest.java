package com.rental.pricing;

import com.rental.model.Car;
import com.rental.model.RentalRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PricingContextTest {

    private final RentalRequest request = new RentalRequest(new Car("Toyota Camry", 50.0, 20000.0), 10, 30);

    @Test
    void contextOnlyDelegatesToTheStrategy() {
        PricingContext context = new PricingContext(r -> 42.0);
        assertEquals(42.0, context.calculate(request), 0.001);
    }

    @Test
    void strategyCanBeSwappedAtRuntime() {
        PricingContext context = new PricingContext(new StandardPricing());
        assertEquals(500.0, context.calculate(request), 0.001);

        context.setStrategy(new LongTermPricing());
        assertEquals(470.0, context.calculate(request), 0.001);
    }

    @Test
    void nullStrategyIsRejected() {
        assertThrows(NullPointerException.class, () -> new PricingContext(null));
    }
}