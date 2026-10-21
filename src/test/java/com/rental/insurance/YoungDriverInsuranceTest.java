package com.rental.insurance;

import com.rental.model.Car;
import com.rental.model.RentalRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class YoungDriverInsuranceTest {

    private final InsuranceStrategy strategy = new YoungDriverInsurance();
    private final Car car = new Car("Toyota Camry", 50.0, 20000.0);

    private double cost(int days, int age) {
        return strategy.calculate(new RentalRequest(car, days, age));
    }

    @Test
    void ageTwentyPaysHighestSurcharge() {
        assertEquals(45.0, cost(3, 20), 0.001);
    }

    @Test
    void ageTwentyOneStartsMediumSurcharge() {
        assertEquals(33.0, cost(3, 21), 0.001);
    }

    @Test
    void ageTwentyFourStillPaysMediumSurcharge() {
        assertEquals(33.0, cost(3, 24), 0.001);
    }

    @Test
    void ageTwentyFiveHasNoSurcharge() {
        assertEquals(15.0, cost(3, 25), 0.001);
    }

    @Test
    void oneDayAtAgeEighteenCostsBasicPlusTen() {
        assertEquals(15.0, cost(1, 18), 0.001);
    }
}