package com.comfihealth.healthrecords.growthmetric;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/growth-metrics")
@RequiredArgsConstructor
public class GrowthMetricController {

    private final GrowthMetricService growthMetricService;

    @PostMapping("/save")
    public GrowthMetric save(@RequestBody GrowthMetricRequest request) {
        return growthMetricService.save(request);
    }
}
