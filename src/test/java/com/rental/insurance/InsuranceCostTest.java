package com.rental.insurance;

import com.rental.model.Car;
import com.rental.model.RentalRequest;
import org.junit.jupiter.api.Test;

import static com.rental.insurance.InsuranceTestSupport.costFor;
import static com.rental.insurance.InsuranceType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InsuranceCostTest {

    private static final Car CAR = new Car("Toyota Camry", 50.0, 20000.0);

    private static RentalRequest request(int days, int driverAge) {
        return new RentalRequest(CAR, days, driverAge);
    }

    @Test
    void basicChargesFlatFeePerDay() {
        assertEquals(15.0, costFor(BASIC, request(3, 30)), 0.001);
    }

    @Test
    void fullCoverageChargesPercentOfCarValuePlusDailyFee() {
        assertEquals(209.0, costFor(FULL_COVERAGE, request(3, 30)), 0.001);
    }

    @Test
    void youngDriverUnder21PaysHighestSurcharge() {
        assertEquals(45.0, costFor(YOUNG_DRIVER, request(3, 20)), 0.001);
    }

    @Test
    void youngDriverFrom21To24PaysMediumSurcharge() {
        assertEquals(33.0, costFor(YOUNG_DRIVER, request(3, 23)), 0.001);
    }

    @Test
    void youngDriverFrom25PaysNoSurcharge() {
        assertEquals(15.0, costFor(YOUNG_DRIVER, request(3, 25)), 0.001);
    }
}