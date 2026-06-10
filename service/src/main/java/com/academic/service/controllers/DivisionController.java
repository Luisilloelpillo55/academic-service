package com.academic.service.controllers;

import com.academic.service.dtos.DivisionDTO;
import com.academic.service.dtos.DivisionUpdateDTO;
import com.academic.service.services.DivisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/divisiones")
@RequiredArgsConstructor
public class DivisionController {
    
    private final DivisionService divisionService;
    
    /**
     * HU-2: GET /api/divisiones - Obtener todas las divisiones
     */
    @GetMapping
    public ResponseEntity<List<DivisionDTO>> obtenerTodasLasDivisiones() {
        return ResponseEntity.ok(divisionService.obtenerTodasLasDivisiones());
    }
    
    /**
     * HU-3: GET /api/divisiones/{id} - Obtener una división por ID (para cargar datos en el formulario de edición)
     */
    @GetMapping("/{id}")
    public ResponseEntity<DivisionDTO> obtenerDivisionPorId(@PathVariable Long id) {
        return ResponseEntity.ok(divisionService.obtenerDivisionPorId(id));
    }
    
    /**
     * HU-1: POST /api/divisiones - Registrar una nueva división
     */
    @PostMapping
    public ResponseEntity<DivisionDTO> crearDivision(@Valid @RequestBody DivisionDTO divisionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(divisionService.crearDivision(divisionDTO));
    }
    
    /**
     * HU-3: PUT /api/divisiones/{id} - Actualizar una división (nombre y/o estado)
     * 
     * Requisitos:
     * - Al editar, cargar en el formulario el Nombre y Clave actuales.
     * - Incluir un toggle para alternar entre Activo/Inactivo.
     * - Al desactivar una división, sus programas asociados ya no deben ser elegibles en las vistas de los estudiantes.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DivisionDTO> actualizarDivision(
            @PathVariable Long id,
            @Valid @RequestBody DivisionUpdateDTO divisionUpdateDTO) {
        return ResponseEntity.ok(divisionService.actualizarDivision(id, divisionUpdateDTO));
    }
}
