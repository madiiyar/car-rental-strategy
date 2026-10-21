package com.rental.insurance;

import com.rental.model.RentalRequest;

public class FullCoverageInsurance implements InsuranceStrategy {

    private static final double CAR_VALUE_RATE = 0.01;
    private static final double DAILY_FEE = 3.0;

    @Override
    public double calculate(RentalRequest request) {
        return request.car().value() * CAR_VALUE_RATE + request.days() * DAILY_FEE;
    }
}