package com.comfihealth.healthrecords.growthmetric;

import com.comfihealth.healthrecords.growthmetric.serializables.*;
import com.comfihealth.profiles.KinProfile;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "growth_metrics")
@NoArgsConstructor
@Getter
@Setter
public class GrowthMetric {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private KinProfile kinProfile;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Height height;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Weight weight;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private HeadCircumference headCircumference;

    private LocalDateTime createdAt; // yield this to jpa magic

    public void setGrowthMetricValue(
            GrowthMetricValueIdentifier identifier,
            List<GrowthMetricAttributes> growthMetricData,
            LocalDateTime updatedAtTime
    ) {
        switch (identifier) {
            case HEIGHT -> {
                this.height = new Height().setLastUpdatedAt(updatedAtTime).setHistory(growthMetricData);
            }
            case WEIGHT -> {
                this.weight = new Weight().setLastUpdatedAt(updatedAtTime).setHistory(growthMetricData);
            }
            case HEAD_CIRCUMFERENCE -> {
                this.headCircumference = new HeadCircumference().setLastUpdatedAt(updatedAtTime).setHistory(growthMetricData);
            }
            default -> {
                throw new IllegalStateException("Unrecognized metric value specified");
            }
        }
    }

    public GrowthMetricBaseValue getGrowthMetricValueInstance(GrowthMetricValueIdentifier identifier) {

        switch (identifier) {
            case HEIGHT -> {
                return this.getHeight() != null ? this.getHeight() : new Height();
            }
            case WEIGHT -> {
                return this.getWeight() != null ? this.getWeight() : new Weight();
            }
            case HEAD_CIRCUMFERENCE -> {
                return this.getHeadCircumference() != null ? this.getHeadCircumference() : new HeadCircumference();
            }
            default -> {
                throw new IllegalStateException("Unrecognized metric value specified"); //todo: implement custom exception here
            }
        }
    }
}
