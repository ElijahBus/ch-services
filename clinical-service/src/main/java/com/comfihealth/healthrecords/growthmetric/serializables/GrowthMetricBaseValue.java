package com.comfihealth.healthrecords.growthmetric.serializables;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public abstract class GrowthMetricBaseValue implements Serializable {

    protected LocalDateTime lastUpdatedAt;

    protected List<GrowthMetricAttributes> history;

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public List<GrowthMetricAttributes> getHistory() {
        if (this.history == null) return new ArrayList<>();

        return history;
    }

    abstract public  GrowthMetricBaseValue setLastUpdatedAt(LocalDateTime dateTime);

    abstract public GrowthMetricBaseValue setHistory(List<GrowthMetricAttributes> history);
}
