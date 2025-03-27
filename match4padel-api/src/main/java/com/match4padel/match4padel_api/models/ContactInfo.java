package com.match4padel.match4padel_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contact_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonProperty("first_name")
    private String firstName;

    @Column(nullable = false)
    @JsonProperty("last_name")
    private String lastName;

    @Column(unique = true)
    private String dni;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Column(nullable = false)
    @JsonProperty("birth_date")
    private LocalDate birthDate;

    private String address;
    private String city;
    private String postalCode;
    private String country;
}
