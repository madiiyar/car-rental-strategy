package com.rental;

import com.rental.data.FleetUtilizationProvider;
import com.rental.data.SimulatedFleetUtilizationProvider;
import com.rental.insurance.BasicInsurance;
import com.rental.insurance.FullCoverageInsurance;
import com.rental.insurance.InsuranceContext;
import com.rental.insurance.InsuranceSelector;
import com.rental.insurance.InsuranceStrategy;
import com.rental.insurance.YoungDriverInsurance;
import com.rental.model.Car;
import com.rental.model.RentalRequest;
import com.rental.pricing.DemandPricing;
import com.rental.pricing.LongTermPricing;
import com.rental.pricing.PricingContext;
import com.rental.pricing.PricingSelector;
import com.rental.pricing.PricingStrategy;
import com.rental.pricing.StandardPricing;

import java.util.List;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        SimulatedFleetUtilizationProvider fleet = new SimulatedFleetUtilizationProvider(0.50);
        PricingSelector pricingSelector = new PricingSelector(fleet);
        InsuranceSelector insuranceSelector = new InsuranceSelector();

        Car camry = new Car("Toyota Camry", 50.0, 20000.0);
        Car bmw = new Car("BMW 7", 150.0, 60000.0);

        header("1. Same request through every strategy");
        showEveryStrategy(new RentalRequest(camry, 10, 30), fleet);

        header("2. Manual choice vs automatic choice (Selector)");
        showManualVersusAutomatic(new RentalRequest(camry, 3, 30), pricingSelector, insuranceSelector);
        showManualVersusAutomatic(new RentalRequest(bmw, 10, 22), pricingSelector, insuranceSelector);

        header("3. Switching the strategy at runtime");
        showRuntimeSwitching(new RentalRequest(camry, 10, 30), fleet, pricingSelector);
    }

    private static void showEveryStrategy(RentalRequest request, FleetUtilizationProvider fleet) {
        System.out.println("  " + describe(request));

        PricingContext pricing = new PricingContext(new StandardPricing());
        for (PricingStrategy strategy : List.of(new StandardPricing(), new LongTermPricing(), new DemandPricing(fleet))) {
            pricing.setStrategy(strategy);
            System.out.println("    price     " + name(strategy) + ": " + money(pricing.calculate(request)));
        }

        InsuranceContext insurance = new InsuranceContext(new BasicInsurance());
        for (InsuranceStrategy strategy : List.of(new BasicInsurance(), new FullCoverageInsurance(), new YoungDriverInsurance())) {
            insurance.setStrategy(strategy);
            System.out.println("    insurance " + name(strategy) + ": " + money(insurance.calculate(request)));
        }
    }

    private static void showManualVersusAutomatic(RentalRequest request,
                                                  PricingSelector pricingSelector,
                                                  InsuranceSelector insuranceSelector) {
        System.out.println("  " + describe(request));
        printQuote("manual   ", new StandardPricing(), new BasicInsurance(), request);
        printQuote("automatic", pricingSelector.select(request), insuranceSelector.select(request), request);
    }

    private static void showRuntimeSwitching(RentalRequest request,
                                             SimulatedFleetUtilizationProvider fleet,
                                             PricingSelector pricingSelector) {
        System.out.println("  " + describe(request));

        PricingStrategy strategy = new StandardPricing();
        PricingContext context = new PricingContext(strategy);
        printPrice("start", strategy, context.calculate(request));

        strategy = new LongTermPricing();
        context.setStrategy(strategy);
        printPrice("switched by hand", strategy, context.calculate(request));

        fleet.setUtilization(0.90);
        System.out.println("    -- fleet utilization rises to 90% --");
        strategy = pricingSelector.select(request);
        context.setStrategy(strategy);
        printPrice("selector decides", strategy, context.calculate(request));
    }

    private static void printQuote(String label, PricingStrategy pricing, InsuranceStrategy insurance,
                                   RentalRequest request) {
        double price = new PricingContext(pricing).calculate(request);
        double cover = new InsuranceContext(insurance).calculate(request);
        System.out.println("    " + label + ": " + name(pricing) + " " + money(price)
                + " + " + name(insurance) + " " + money(cover) + " = " + money(price + cover));
    }

    private static void printPrice(String label, PricingStrategy strategy, double price) {
        System.out.println("    " + label + ": " + name(strategy) + " -> " + money(price));
    }

    private static String describe(RentalRequest request) {
        return request.car().model() + ", " + request.days() + " days, driver age " + request.driverAge();
    }

    private static String name(Object strategy) {
        return strategy.getClass().getSimpleName();
    }

    private static String money(double amount) {
        return String.format(Locale.US, "%.2f", amount);
    }

    private static void header(String title) {
        System.out.println();
        System.out.println("=== " + title + " ===");
    }
}