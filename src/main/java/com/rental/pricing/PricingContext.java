package com.rental.pricing;

import com.rental.model.RentalRequest;
import java.util.Objects;

public class PricingContext {

    private PricingStrategy strategy;

    public PricingContext(PricingStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy must not be null");
    }

    public void setStrategy(PricingStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy must not be null");
    }

    public double calculate(RentalRequest request) {
        return strategy.calculate(request);
    }
}