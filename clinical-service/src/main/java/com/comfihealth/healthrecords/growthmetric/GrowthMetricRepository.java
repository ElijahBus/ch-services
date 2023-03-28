package com.comfihealth.healthrecords.growthmetric;

import com.comfihealth.healthrecords.growthmetric.serializables.GrowthMetricAttributes;
import com.comfihealth.healthrecords.growthmetric.serializables.Height;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Stack;

public interface GrowthMetricRepository extends JpaRepository<GrowthMetric, Long> {

    @Modifying
    @Transactional
    @Query("update GrowthMetric gm set gm.height = ?1 where gm.id = ?2")
    void updateGrowthMetricHeight(Height data, Long growthMetricId);
}
