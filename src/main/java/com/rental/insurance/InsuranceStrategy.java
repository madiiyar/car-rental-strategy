package com.rental.insurance;

import com.rental.model.RentalRequest;

public interface InsuranceStrategy {

    double calculate(RentalRequest request);
}