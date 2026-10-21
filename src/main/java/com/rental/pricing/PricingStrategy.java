package com.rental.pricing;

import com.rental.model.RentalRequest;

public interface PricingStrategy {

    double calculate(RentalRequest request);
}