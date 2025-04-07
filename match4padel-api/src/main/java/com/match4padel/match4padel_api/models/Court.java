package com.match4padel.match4padel_api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.match4padel.match4padel_api.models.enums.CourtStatus;
import com.match4padel.match4padel_api.models.enums.CourtType;
import com.match4padel.match4padel_api.models.enums.CourtZone;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "courts")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourtStatus courtStatus = CourtStatus.AVAILABLE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourtZone courtZone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourtType courtType;

    @Column(nullable = false)
    private BigDecimal pricePerMatch;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

