package com.farmAttic.models;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

import jakarta.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Introspected
@Entity
@Builder
@Table(name="tbm_user")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id",unique = true,nullable = false ,length = 36)
    private UUID userId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;
}
