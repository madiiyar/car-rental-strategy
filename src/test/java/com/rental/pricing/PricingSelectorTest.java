package com.rental.pricing;

import com.rental.model.Car;
import com.rental.model.RentalRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PricingSelectorTest {

    private static final Car CAR = new Car("Toyota Camry", 50.0, 20000.0);

    private static RentalRequest request(int days) {
        return new RentalRequest(CAR, days, 30);
    }

    private static PricingSelector selectorWithUtilization(double utilization) {
        return new PricingSelector(new FakeFleetUtilizationProvider(utilization));
    }

    @Test
    void highUtilizationPicksDemandPricing() {
        assertInstanceOf(DemandPricing.class, selectorWithUtilization(0.9).select(request(3)));
    }

    @Test
    void demandRuleWinsOverLongTermRule() {
        assertInstanceOf(DemandPricing.class, selectorWithUtilization(0.9).select(request(10)));
    }

    @Test
    void utilizationExactlyAtThresholdIsNotHighDemand() {
        assertInstanceOf(StandardPricing.class, selectorWithUtilization(0.8).select(request(3)));
    }

    @Test
    void sevenDaysPickLongTermPricing() {
        assertInstanceOf(LongTermPricing.class, selectorWithUtilization(0.5).select(request(7)));
    }

    @Test
    void sixDaysPickStandardPricing() {
        assertInstanceOf(StandardPricing.class, selectorWithUtilization(0.5).select(request(6)));
    }

    @Test
    void selectionFollowsTheCurrentUtilization() {
        FakeFleetUtilizationProvider provider = new FakeFleetUtilizationProvider(0.3);
        PricingSelector selector = new PricingSelector(provider);

        assertInstanceOf(StandardPricing.class, selector.select(request(3)));
        provider.setUtilization(0.95);
        assertInstanceOf(DemandPricing.class, selector.select(request(3)));
    }
}