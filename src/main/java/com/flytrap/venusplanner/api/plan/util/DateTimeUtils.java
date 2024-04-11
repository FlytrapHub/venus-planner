package com.flytrap.venusplanner.api.plan.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeUtils {
    public static ZonedDateTime toZonedDateTime(Instant instant) {
        return instant.atZone(ZoneId.systemDefault());
    }

    public static Instant toInstant(ZonedDateTime zonedDateTime) {
        return zonedDateTime.toInstant();
    }
}
