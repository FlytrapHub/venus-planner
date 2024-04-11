package com.flytrap.venusplanner.api.plan.domain;

import com.flytrap.venusplanner.api.plan.business.service.EndOptionCalculate;
import com.flytrap.venusplanner.api.plan.util.DateTimeUtils;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public enum Frequency implements EndOptionCalculate {
    WEEKLY(ChronoUnit.WEEKS) {
        @Override
        public Instant calculateEndDate(Instant startDate, int count) {
            ZonedDateTime zdtStart = DateTimeUtils.toZonedDateTime(startDate);
            return DateTimeUtils.toInstant(zdtStart.plusWeeks(count - 1));
        }

        @Override
        public int calculateCount(Instant startDate, Instant endDate) {
            ZonedDateTime zdtStart = DateTimeUtils.toZonedDateTime(startDate);
            ZonedDateTime zdtEnd = DateTimeUtils.toZonedDateTime(endDate);
            return (int) ChronoUnit.WEEKS.between(zdtStart, zdtEnd) + 1;
        }
    },
    MONTHLY(ChronoUnit.MONTHS) {
        @Override
        public Instant calculateEndDate(Instant startDate, int count) {
            ZonedDateTime zdtStart = startDate.atZone(ZoneId.systemDefault());
            ZonedDateTime zdtEnd = zdtStart.plusMonths(count - 1);
            return zdtEnd.toInstant();
        }

        @Override
        public int calculateCount(Instant startDate, Instant endDate) {
            ZonedDateTime zdtStart = startDate.atZone(ZoneId.systemDefault());
            ZonedDateTime zdtEnd = endDate.atZone(ZoneId.systemDefault());
            long monthsBetween = ChronoUnit.MONTHS.between(zdtStart, zdtEnd);
            return (int) monthsBetween + 1;
        }
    },
    YEARLY(ChronoUnit.YEARS) {
        @Override
        public Instant calculateEndDate(Instant startDate, int count) {
            ZonedDateTime zdtStart = startDate.atZone(ZoneId.systemDefault());
            ZonedDateTime zdtEnd = zdtStart.plusYears(count - 1);
            return zdtEnd.toInstant();
        }

        @Override
        public int calculateCount(Instant startDate, Instant endDate) {
            ZonedDateTime zdtStart = startDate.atZone(ZoneId.systemDefault());
            ZonedDateTime zdtEnd = endDate.atZone(ZoneId.systemDefault());
            long yearsBetween = ChronoUnit.YEARS.between(zdtStart, zdtEnd);
            return (int) yearsBetween + 1;
        }
    };

    private final ChronoUnit chronoUnit;

    Frequency(ChronoUnit chronoUnit) {
        this.chronoUnit = chronoUnit;
    }

    public ChronoUnit getChronoUnit() {
        return chronoUnit;
    }
}
