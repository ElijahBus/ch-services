package com.comfihealth.healthrecords.growthmetric;

public record GrowthMetricRequest(
        String date,
        String value,
        Long kinProfileId
) {
}
