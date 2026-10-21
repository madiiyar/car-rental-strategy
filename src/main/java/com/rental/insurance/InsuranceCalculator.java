package com.rental.insurance;

import com.rental.model.RentalRequest;

public class InsuranceCalculator {

    public double calculate(InsuranceType type, RentalRequest request) {
        return switch (type) {
            case BASIC -> basic(request);
            case FULL_COVERAGE -> fullCoverage(request);
            case YOUNG_DRIVER -> youngDriver(request);
        };
    }

    private double basic(RentalRequest request) {
        return request.days() * 5.0;
    }

    private double fullCoverage(RentalRequest request) {
        return request.car().value() * 0.01 + request.days() * 3.0;
    }

    private double youngDriver(RentalRequest request) {
        double surchargePerDay = 0;
        if (request.driverAge() < 21) {
            surchargePerDay = 10.0;
        } else if (request.driverAge() < 25) {
            surchargePerDay = 6.0;
        }
        return basic(request) + request.days() * surchargePerDay;
    }
}