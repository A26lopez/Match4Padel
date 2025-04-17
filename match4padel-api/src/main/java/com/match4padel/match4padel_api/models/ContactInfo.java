package com.match4padel.match4padel_api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(min = 2, max = 14, message = "El nombre debe tener entre 2 y 14 caracteres.")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "El nombre solo puede contener letras y espacios.")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Los apellidos no pueden estar vacíos.")
    @Size(min = 2, max = 30, message = "Los apellidos deben tener entre 2 y 30 caracteres.")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "Los apellidos solo pueden contener letras y espacios.")
    private String lastName;

    @Column(unique = true)
    @NotBlank(message = "El NIF no puede estar vacío.")
    @Pattern(regexp = "^[0-9]{8}[A-Za-z]$", message = "El NIF no es válido.")
    private String nif;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El email no puede estar vacío.")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "El formato del email no es válido.")
    private String email;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El número de teléfono no puede estar vacío.")
    @Pattern(regexp = "^[67][0-9]{8}$", message = "El número de teléfono no es válido.")
    private String phoneNumber;

    @Column(nullable = false)
    @NotNull(message = "La fecha de nacimiento no puede ser nula.")
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual.")
    private LocalDate birthDate;

    @Size(max = 30, message = "Max 30 caracteres.")
    private String address;
    
    @Size(max = 12, message = "Max 12 caracteres.")
    private String city;
    
    @Pattern(regexp = "^[0-9]{5}$", message = "Código postal no válido.")
    private String postalCode;
    
    @Size(max = 12, message = "Max 12 caracteres.")
    private String country;
}
