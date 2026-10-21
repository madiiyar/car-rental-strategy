package com.rental.insurance;

import com.rental.model.RentalRequest;

public class InsuranceSelector {

    private static final int YOUNG_DRIVER_AGE_LIMIT = 25;
    private static final double EXPENSIVE_CAR_VALUE = 30000.0;

    private final InsuranceStrategy basic = new BasicInsurance();
    private final InsuranceStrategy fullCoverage = new FullCoverageInsurance();
    private final InsuranceStrategy youngDriver = new YoungDriverInsurance();

    public InsuranceStrategy select(RentalRequest request) {
        if (request.driverAge() < YOUNG_DRIVER_AGE_LIMIT) {
            return youngDriver;
        }
        if (request.car().value() >= EXPENSIVE_CAR_VALUE) {
            return fullCoverage;
        }
        return basic;
    }
}