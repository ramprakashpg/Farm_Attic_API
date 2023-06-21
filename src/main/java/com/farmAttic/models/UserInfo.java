package com.farmAttic.models;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Introspected
@Entity
@Table(name="tbm_user_details")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",unique = true,nullable = false ,length = 36)
    private UUID userId;

    @Column(name = "postal_address", nullable = false)
    private String postalAddress;

    @Column(name = "contact", nullable = false)
    private int contact;

    @Column(name = "zip_code", nullable = false)
    private int zipCode;
}
