package com.rental.insurance;

import com.rental.model.Car;
import com.rental.model.RentalRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class InsuranceStrategiesParameterizedTest {

    static Stream<Arguments> strategies() {
        return Stream.of(
                Arguments.of("BasicInsurance", new BasicInsurance()),
                Arguments.of("FullCoverageInsurance", new FullCoverageInsurance()),
                Arguments.of("YoungDriverInsurance", new YoungDriverInsurance()));
    }

    @ParameterizedTest(name = "{0} is never negative")
    @MethodSource("strategies")
    void costIsNeverNegative(String name, InsuranceStrategy strategy) {
        Car car = new Car("Toyota Camry", 50.0, 20000.0);
        for (int days = 1; days <= 60; days++) {
            for (int age : new int[]{18, 20, 23, 30, 70}) {
                double cost = strategy.calculate(new RentalRequest(car, days, age));
                assertTrue(cost >= 0.0, name + " returned " + cost + " for " + days + " days, age " + age);
            }
        }
    }
}