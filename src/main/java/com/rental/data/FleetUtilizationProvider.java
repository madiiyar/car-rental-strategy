package com.rental.data;

public interface FleetUtilizationProvider {

    /** Share of cars currently rented out, from 0.0 (all free) to 1.0 (all rented). */
    double currentUtilization();
}