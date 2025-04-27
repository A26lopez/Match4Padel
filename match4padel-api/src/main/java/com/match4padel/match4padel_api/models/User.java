package com.match4padel.match4padel_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.match4padel.match4padel_api.models.enums.Level;
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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

    @Valid
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Elige un nivel.")
    private Level level;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private AccountInfo accountInfo;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private ContactInfo contactInfo;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private AccountSecurity accountSecurity;
}
