package com.comfihealth.healthrecords.growthmetric;

import com.comfihealth.healthrecords.growthmetric.serializables.HeadCircumference;
import com.comfihealth.healthrecords.growthmetric.serializables.Height;
import com.comfihealth.healthrecords.growthmetric.serializables.Weight;
import com.comfihealth.profiles.KinProfile;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@Entity
@Table(name = "growth_metrics")
@NoArgsConstructor
@Getter
@Setter
public class GrowthMetric {

    @Id
    @SequenceGenerator(
            name = "growth_metrics_sequence",
            sequenceName = "growth_metrics_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "growth_metrics_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @OneToOne
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

    public GrowthMetric(Height height,
                        Weight weight,
                        HeadCircumference hc,
                        KinProfile kinProfile,
                        LocalDateTime createdAt
    ) {
        this.height = height;
        this.weight = weight;
        this.headCircumference = hc;
        this.kinProfile = kinProfile;
        this.createdAt = createdAt;
    }
}
