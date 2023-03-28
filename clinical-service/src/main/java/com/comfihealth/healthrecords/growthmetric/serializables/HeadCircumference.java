package com.comfihealth.healthrecords.growthmetric.serializables;

import java.time.LocalDateTime;
import java.util.List;


public class HeadCircumference extends GrowthMetricBaseValue {

    @Override
    public HeadCircumference setLastUpdatedAt(LocalDateTime dateTime) {
        this.lastUpdatedAt = dateTime;
        return this;
    }

    @Override
    public HeadCircumference setHistory(List<GrowthMetricAttributes> history) {
        this.history = history;
        return this;
    }
}
