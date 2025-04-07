package com.match4padel.match4padel_api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account_security")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountSecurity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    @Size(min = 8, max = 16, message = "La contraseña debe tener entre 8 y 16 caracteres.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._,-])[A-Za-z\\d@$!%*?&._,-]+$",
            message = "La contraseña debe contener al menos una letra mayúscula, una minúscula, un número y un caracter especial ( @$!%*?&._,- )")
    private String password;

    @Column(nullable = false)
    private String passwordHash;

}
