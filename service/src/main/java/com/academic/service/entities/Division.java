package com.academic.service.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "divisiones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la división es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "La clave es obligatoria")
    @Column(nullable = false, unique = true, length = 50)
    private String clave;

    // Por defecto, al crear, la división estará activa
    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;

    // Relación OneToMany: Una división tiene muchos programas.
    // cascade = CascadeType.ALL: Si guardas/eliminas una división, afecta a sus programas.
    // orphanRemoval = true: Si quitas un programa de esta lista, se elimina de la BD.
    @OneToMany(mappedBy = "division", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProgramaEducativo> programas = new ArrayList<>();

    // Método auxiliar (Helper) para mantener la relación bidireccional sincronizada
    public void addPrograma(ProgramaEducativo programa) {
        programas.add(programa);
        programa.setDivision(this);
    }

    public void removePrograma(ProgramaEducativo programa) {
        programas.remove(programa);
        programa.setDivision(null);
    }
}