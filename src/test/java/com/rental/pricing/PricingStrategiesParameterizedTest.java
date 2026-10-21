package com.rental.pricing;

import com.rental.model.Car;
import com.rental.model.RentalRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PricingStrategiesParameterizedTest {

    static Stream<Arguments> strategies() {
        return Stream.of(
                Arguments.of("StandardPricing", new StandardPricing()),
                Arguments.of("LongTermPricing", new LongTermPricing()),
                Arguments.of("DemandPricing", new DemandPricing(new FakeFleetUtilizationProvider(0.5))));
    }

    @ParameterizedTest(name = "{0} is never negative")
    @MethodSource("strategies")
    void priceIsNeverNegative(String name, PricingStrategy strategy) {
        Car car = new Car("Toyota Camry", 50.0, 20000.0);
        for (int days = 1; days <= 60; days++) {
            double price = strategy.calculate(new RentalRequest(car, days, 30));
            assertTrue(price >= 0.0, name + " returned " + price + " for " + days + " days");
        }
    }
}