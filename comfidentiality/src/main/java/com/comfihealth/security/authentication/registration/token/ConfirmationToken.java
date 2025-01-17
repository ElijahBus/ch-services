package com.comfihealth.security.authentication.registration.token;

import com.comfihealth.security.authentication.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "confirmation_tokens")
@Getter
@Setter
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "confirmation_token_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private boolean confirmed = false;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public ConfirmationToken(String token,
                             String destination,
                             User user,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt
    ) {
        this.token = token;
        this.user = user;
        this.destination = destination;
        this.createdAt =  createdAt;
        this.expiresAt = expiresAt;
    }

}
