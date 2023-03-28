package com.comfihealth.healthrecords.growthmetric;

import com.comfihealth.healthrecords.growthmetric.serializables.GrowthMetricBaseValue;
import com.comfihealth.healthrecords.growthmetric.serializables.GrowthMetricValueIdentifier;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/growth-metrics")
@RequiredArgsConstructor
public class GrowthMetricController {

    private final GrowthMetricService growthMetricService;

    private final GrowthMetricRepository growthMetricRepository;

    //todo: return custom json responses with ResponseEntity
    @PostMapping("/save")
    public String save(@RequestBody GrowthMetricRequest request) {
        return growthMetricService.save(request);
    }

    @GetMapping(path = "{gmId}")
    Optional<GrowthMetric> getGM(@PathVariable Long gmId) {
        return growthMetricRepository.findById(gmId);
    }

    @GetMapping("/{id}/{metric}")
    GrowthMetricBaseValue getGrowthMetricValues(@PathVariable Long id, @PathVariable GrowthMetricValueIdentifier metric) {
        return growthMetricService.getGrowthMetricValues(id, metric);
    }
}
