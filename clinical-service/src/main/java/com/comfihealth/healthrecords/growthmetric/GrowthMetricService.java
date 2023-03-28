package com.comfihealth.healthrecords.growthmetric;

import com.comfihealth.healthrecords.growthmetric.serializables.GrowthMetricAttributes;
import com.comfihealth.healthrecords.growthmetric.serializables.GrowthMetricBaseValue;
import com.comfihealth.healthrecords.growthmetric.serializables.GrowthMetricValueIdentifier;
import com.comfihealth.profiles.KinProfile;
import com.comfihealth.profiles.KinProfileRepository;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GrowthMetricService {

    private final GrowthMetricRepository growthMetricRepository;

    private final KinProfileRepository kinProfileRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public String save(GrowthMetricRequest request) {

        var kinProfile = kinProfileRepository.findById(request.getKinProfileId()).orElseThrow(); // todo: use custom not found exception here

        if (kinProfile.getGrowthMetric() != null) {
            return updateGrowthMetric(request, kinProfile);
        }

        return storeNewGrowthMetric(request, kinProfile);
    }

    private String storeNewGrowthMetric(GrowthMetricRequest request, KinProfile kinProfile) {

        var newGrowthMetricData = new ArrayList<GrowthMetricAttributes>();
        newGrowthMetricData.add(new GrowthMetricAttributes(LocalDateTime.now().toString(), request.getValue()));

        var newGrowthMetric = new GrowthMetric();
        newGrowthMetric.setGrowthMetricValue(request.getMetric(), newGrowthMetricData, LocalDateTime.now() );
        newGrowthMetric.setKinProfile(kinProfile);
        newGrowthMetric.setCreatedAt(LocalDateTime.now());

        growthMetricRepository.save(newGrowthMetric);

        return "Growth metric successfully saved";
    }

    private String updateGrowthMetric(GrowthMetricRequest request, KinProfile kinProfile) {

        var metricValueInstance = kinProfile.getGrowthMetric().getGrowthMetricValueInstance(request.getMetric());
        var growthMetricData = metricValueInstance.getHistory();

        growthMetricData.add(new GrowthMetricAttributes(LocalDateTime.now().toString(), request.getValue()));

        executeGrowthMetricUpdate(metricValueInstance, growthMetricData, request.getMetric().toString().toLowerCase(),
                kinProfile.getGrowthMetric().getId());

        return "Growth metric successfully saved";
    }

    @Modifying
    public void executeGrowthMetricUpdate(GrowthMetricBaseValue metricBaseValue, List<GrowthMetricAttributes> data,
                                          String metric, Long id) {

        entityManager.joinTransaction();

        entityManager
                .createNativeQuery("UPDATE growth_metrics SET " + metric + " = :value WHERE id = :id")
                .setParameter("id", id)
                .unwrap(NativeQuery.class)
                .setParameter(
                        "value",
                        metricBaseValue.setLastUpdatedAt(LocalDateTime.now()).setHistory(data),
                        new JsonType(metricBaseValue.getClass()))
                .executeUpdate();

        entityManager.close();
    }

    public GrowthMetricBaseValue getGrowthMetricValues(Long id, GrowthMetricValueIdentifier metric) {
        return growthMetricRepository.findById(id).orElseThrow().getGrowthMetricValueInstance(metric);
    }
}
