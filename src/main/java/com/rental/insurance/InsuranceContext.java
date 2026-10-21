package com.rental.insurance;

import com.rental.model.RentalRequest;
import java.util.Objects;

public class InsuranceContext {

    private InsuranceStrategy strategy;

    public InsuranceContext(InsuranceStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy must not be null");
    }

    public void setStrategy(InsuranceStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy must not be null");
    }

    public double calculate(RentalRequest request) {
        return strategy.calculate(request);
    }
}