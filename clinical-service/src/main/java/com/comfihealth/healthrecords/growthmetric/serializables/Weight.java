package com.comfihealth.healthrecords.growthmetric.serializables;

import java.time.LocalDateTime;
import java.util.List;


public class Weight extends GrowthMetricBaseValue {
    @Override
    public Weight setLastUpdatedAt(LocalDateTime dateTime) {
        this.lastUpdatedAt = dateTime;
        return this;
    }

    @Override
    public Weight setHistory(List<GrowthMetricAttributes> history) {
        this.history = history;
        return this;
    }
}
