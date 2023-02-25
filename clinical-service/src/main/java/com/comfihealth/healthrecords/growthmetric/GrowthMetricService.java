package com.comfihealth.healthrecords.growthmetric;

import com.comfihealth.healthrecords.growthmetric.serializables.GrowthMetricAttributes;
import com.comfihealth.healthrecords.growthmetric.serializables.Height;
import com.comfihealth.profiles.KinProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Stack;

@Service
@RequiredArgsConstructor
public class GrowthMetricService {

    private final GrowthMetricRepository growthMetricRepository;

    private final KinProfileRepository kinProfileRepository;

    public GrowthMetric save(GrowthMetricRequest request) {
        var kinProfile = kinProfileRepository.findById(request.kinProfileId()).orElseThrow(); // use custom not found exception here

        // check if the metric exists and update it, else create a new one

        var gms = new Stack<GrowthMetricAttributes>();
        gms.push(new GrowthMetricAttributes(request.date(), request.value()));

        return growthMetricRepository.save(new GrowthMetric(
                new Height(gms),
                null,
                null,
                kinProfile,
                LocalDateTime.now()
        ));
    }
}
