package com.flytrap.venusplanner.api.plan.business.service;

import java.time.Instant;

public interface EndOptionCalculate {
    Instant calculateEndDate(Instant startDate, int count);
    int calculateCount(Instant startDate, Instant endDate);
}
