package com.academic.service.services;

import com.academic.service.dtos.DivisionDTO;
import com.academic.service.dtos.DivisionUpdateDTO;
import com.academic.service.entities.Division;
import com.academic.service.exceptions.DivisionClaveAlreadyExistsException;
import com.academic.service.exceptions.DivisionNotFoundException;
import com.academic.service.repositories.DivisionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DivisionService {
    
    private final DivisionRepository divisionRepository;
    
    /**
     * HU-2: Obtener todas las divisiones
     */
    public List<DivisionDTO> obtenerTodasLasDivisiones() {
        return divisionRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    /**
     * HU-3: Obtener una división por ID (para cargar en el formulario de edición)
     */
    public DivisionDTO obtenerDivisionPorId(Long id) {
        Division division = divisionRepository.findById(id)
                .orElseThrow(() -> new DivisionNotFoundException(id));
        return convertirADTO(division);
    }
    
    /**
     * HU-1: Crear una nueva división
     */
    @Transactional
    public DivisionDTO crearDivision(DivisionDTO divisionDTO) {
        // Validar que la clave no exista
        if (divisionRepository.existsByClave(divisionDTO.getClave())) {
            throw new DivisionClaveAlreadyExistsException(divisionDTO.getClave());
        }
        
        Division division = Division.builder()
                .nombre(divisionDTO.getNombre())
                .clave(divisionDTO.getClave())
                .activo(true) // Por defecto, la división se crea activa
                .build();
        
        Division divisionGuardada = divisionRepository.save(division);
        return convertirADTO(divisionGuardada);
    }
    
    /**
     * HU-3: Actualizar una división (nombre y/o estado)
     * 
     * Requisitos:
     * - Al editar, cargar en el formulario el Nombre y Clave actuales.
     * - Incluir un toggle para alternar entre Activo/Inactivo.
     * - Al desactivar una división, sus programas asociados ya no deben ser elegibles en las vistas de los estudiantes.
     */
    @Transactional
    public DivisionDTO actualizarDivision(Long id, DivisionUpdateDTO divisionUpdateDTO) {
        Division division = divisionRepository.findById(id)
                .orElseThrow(() -> new DivisionNotFoundException(id));
        
        // Actualizar el nombre
        if (divisionUpdateDTO.getNombre() != null && !divisionUpdateDTO.getNombre().isBlank()) {
            division.setNombre(divisionUpdateDTO.getNombre());
        }
        
        // Actualizar el estado (activo/inactivo)
        if (divisionUpdateDTO.getActivo() != null) {
            division.setActivo(divisionUpdateDTO.getActivo());
            // Nota: Al desactivar una división, sus programas asociados ya no serán elegibles
            // en las vistas de los estudiantes. Esto se controla a nivel de consulta en el repositorio.
        }
        
        Division divisionActualizada = divisionRepository.save(division);
        return convertirADTO(divisionActualizada);
    }
    
    // Método auxiliar para convertir Entity a DTO
    private DivisionDTO convertirADTO(Division division) {
        return DivisionDTO.builder()
                .id(division.getId())
                .nombre(division.getNombre())
                .clave(division.getClave())
                .activo(division.getActivo())
                .build();
    }
}
