package com.rental.pricing;

import com.rental.model.RentalRequest;

public class LongTermPricing implements PricingStrategy {

    private static final int FULL_PRICE_LAST_DAY = 6;
    private static final int DISCOUNT_LAST_DAY = 29;
    private static final double DISCOUNT_FACTOR = 0.85;
    private static final double DEEP_DISCOUNT_FACTOR = 0.70;

    @Override
    public double calculate(RentalRequest request) {
        int days = request.days();
        int fullPriceDays = Math.min(days, FULL_PRICE_LAST_DAY);
        int discountedDays = Math.max(0, Math.min(days, DISCOUNT_LAST_DAY) - FULL_PRICE_LAST_DAY);
        int deepDiscountDays = Math.max(0, days - DISCOUNT_LAST_DAY);

        double weightedDays = fullPriceDays
                + discountedDays * DISCOUNT_FACTOR
                + deepDiscountDays * DEEP_DISCOUNT_FACTOR;
        return weightedDays * request.car().dailyRate();
    }
}