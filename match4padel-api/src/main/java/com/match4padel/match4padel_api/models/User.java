package com.match4padel.match4padel_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty("account_info")
    @JoinColumn(nullable = false, unique = true, name = "account_info_id")
    private AccountInfo accountInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty("contact_info")
    @JoinColumn(nullable = false, unique = true, name = "contact_info_id")
    private ContactInfo contactInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty("account_security")
    @JoinColumn(nullable = false, unique = true, name = "account_security_id")
    private AccountSecurity accountSecurity;
    
    
}

enum Level{
    beginner,
    intermediate,
    advanced
}
