package com.comfihealth.healthrecords.growthmetric.serializables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class GrowthMetricAttributes implements Serializable {
    private String name;
    private String value;
}
