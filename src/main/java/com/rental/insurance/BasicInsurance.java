package com.rental.insurance;

import com.rental.model.RentalRequest;

public class BasicInsurance implements InsuranceStrategy {

    public static final double DAILY_FEE = 5.0;

    @Override
    public double calculate(RentalRequest request) {
        return request.days() * DAILY_FEE;
    }
}