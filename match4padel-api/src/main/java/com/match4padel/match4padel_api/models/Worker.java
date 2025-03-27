package com.match4padel.match4padel_api.models;

import jakarta.persistence.Entity;
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
@Table(name = "workers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private AccountInfo accountInfo;

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private ContactInfo contactInfo;

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private AccountSecurity accountSecurity;

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private WorkInfo workInfo;
}
