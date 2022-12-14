package com.comfihealth.notification;

import lombok.*;

import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import java.time.LocalDateTime;

@Entity(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(
            generator = "notification_sequence",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "notification_sequence",
            sequenceName = "notification_sequence",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Notification(String userId, String destination, String message, LocalDateTime createdAt) {
        this.userId = userId;
        this.message = message;
        this.destination = destination;
        this.createdAt = createdAt;
    }
}
