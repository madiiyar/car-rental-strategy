package com.rental.insurance;

import com.rental.model.RentalRequest;

final class InsuranceTestSupport {

    private InsuranceTestSupport() {
    }

    static double costFor(InsuranceType type, RentalRequest request) {
        return new InsuranceCalculator().calculate(type, request);
    }
}