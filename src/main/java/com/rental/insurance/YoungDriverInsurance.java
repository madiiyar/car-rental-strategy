package com.rental.insurance;

import com.rental.model.RentalRequest;

public class YoungDriverInsurance implements InsuranceStrategy {

    private static final double SURCHARGE_UNDER_21 = 10.0;
    private static final double SURCHARGE_21_TO_24 = 6.0;

    @Override
    public double calculate(RentalRequest request) {
        double baseFee = request.days() * BasicInsurance.DAILY_FEE;
        return baseFee + request.days() * surchargePerDay(request.driverAge());
    }

    private double surchargePerDay(int age) {
        if (age < 21) {
            return SURCHARGE_UNDER_21;
        }
        if (age < 25) {
            return SURCHARGE_21_TO_24;
        }
        return 0.0;
    }
}