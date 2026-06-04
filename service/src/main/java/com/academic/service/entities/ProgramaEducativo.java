package com.academic.service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "programas_educativos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramaEducativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del programa es obligatorio")
    @Column(nullable = false)
    private String nombre;

    // Podemos usar un Enum para restringir la entrada a INTENSIVA o MIXTA a nivel base de datos
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Modalidad modalidad;

    // Relación ManyToOne: Muchos programas pertenecen a una división.
    // FetchType.LAZY es una buena práctica de rendimiento.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "division_id", nullable = false)
    @JsonIgnore // Evita un bucle infinito al serializar la respuesta en JSON
    private Division division;

    public enum Modalidad {
        INTENSIVA,
        MIXTA
    }
}