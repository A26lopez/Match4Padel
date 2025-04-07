package com.match4padel.match4padel_api.models;

import com.match4padel.match4padel_api.models.enums.WorkerRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "work_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkerRole role = WorkerRole.EMPLOYEE;

    @Column(nullable = false)
    private LocalDate hireDateStart;

    private LocalDate hireDateEnd;

    @Column(nullable = false)
    private BigDecimal salary;

    @Column(nullable = false, unique = true)
    private String socialSecurityNumber;

    @Column(nullable = false, unique = true)
    private String bankAccount;

}
