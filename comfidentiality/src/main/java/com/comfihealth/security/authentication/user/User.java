package com.comfihealth.security.authentication.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents User being authenticated.
 * todo: Find a way of using migration files for fields definition
 *
 * @author elijah
 * @since 0.1.0
 */
@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @SequenceGenerator(
            name = "user_sequence",
            allocationSize = 1,
            sequenceName = "user_sequence"
    )
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = true)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String sex;

    @Column(unique = true)
    private String email; //COMMENT: Should be unique per country code

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    private String address;

    @Column(nullable = false)
    private boolean accountExpired = false;
    @Column(nullable = false)
    private boolean accountLocked = false;
    @Column(nullable = false)
    private boolean credentialsExpired = false;
    @Column(nullable = false)
    private boolean enabled = false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime emailVerifiedAt;
    private LocalDateTime phoneNumberVerifiedAt;
    private LocalDateTime lockedAt;
    private LocalDateTime expiredAt;

    public User(String username,
                String password,
                String firstName,
                String lastName,
                String sex,
                String email,
                String phoneNumber,
                String country,
                String city,
                String address
    ) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.city = city;
        this.address = address;
    }
}
