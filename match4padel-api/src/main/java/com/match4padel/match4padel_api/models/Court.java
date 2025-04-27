package com.match4padel.match4padel_api.models;

import com.match4padel.match4padel_api.models.enums.CourtType;
import com.match4padel.match4padel_api.models.enums.CourtZone;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "courts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Elige un nombre.")
    private String name;

    private String description;

    private String pictureUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Elige una zona.")
    private CourtZone courtZone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Elige un tipo.")
    private CourtType courtType;

    @Column(nullable = false)
    @NotNull(message = "Elige un precio.")
    private BigDecimal pricePerMatch;
}
