package com.comfihealth.healthrecords.growthmetric;

import com.comfihealth.healthrecords.growthmetric.serializables.GrowthMetricValueIdentifier;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class  GrowthMetricRequest {
    private GrowthMetricValueIdentifier metric;

    private String value;

    private Long kinProfileId;
}
