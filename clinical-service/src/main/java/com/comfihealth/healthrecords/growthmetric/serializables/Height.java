package com.comfihealth.healthrecords.growthmetric.serializables;

import java.time.LocalDateTime;
import java.util.List;

public class Height extends GrowthMetricBaseValue {

    @Override
    public Height setLastUpdatedAt(LocalDateTime dateTime) {
        this.lastUpdatedAt = dateTime;
        return this;
    }

    @Override
    public Height setHistory(List<GrowthMetricAttributes> history) {
        this.history = history;
        return this;
    }
}

