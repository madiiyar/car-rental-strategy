package com.rental.insurance;

import com.rental.model.RentalRequest;

final class InsuranceTestSupport {

    private InsuranceTestSupport() {
    }

    static double costFor(InsuranceType type, RentalRequest request) {
        InsuranceStrategy strategy = switch (type) {
            case BASIC -> new BasicInsurance();
            case FULL_COVERAGE -> new FullCoverageInsurance();
            case YOUNG_DRIVER -> new YoungDriverInsurance();
        };
        return new InsuranceContext(strategy).calculate(request);
    }
}