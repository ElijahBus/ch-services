package com.comfihealth.profiles;

import com.comfihealth.healthrecords.growthmetric.serializables.GrowthMetricBaseValue;
import com.comfihealth.healthrecords.growthmetric.serializables.GrowthMetricValueIdentifier;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/kin-profile")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class KinProfileController {

    private final KinProfileService kinProfileService;

    @PostMapping("/create")
    KinProfile createKinProfile(@RequestBody KinProfileRequest request) {
        return kinProfileService.createKinProfile(request);
    }

    @GetMapping("/{id}")
    Optional<KinProfile> getKinProfile(@PathVariable Long id) {
        return kinProfileService.getKinProfile(id);
    }

    @GetMapping("/{kinProfileId}/growth-metric/{metric}")
    GrowthMetricBaseValue getKinProfileGrowthMetric(@PathVariable Long kinProfileId,
                                           @PathVariable GrowthMetricValueIdentifier metric
    ) {
        return kinProfileService.getKinProfileGrowthMetric(kinProfileId, metric);
    }
}
