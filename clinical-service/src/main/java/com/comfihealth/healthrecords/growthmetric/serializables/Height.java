package com.comfihealth.healthrecords.growthmetric.serializables;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Stack;

@Getter
@Setter
@AllArgsConstructor
public class Height implements Serializable {
    private Stack<GrowthMetricAttributes> data;
}

