package com.hospital.specialty_service.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.hospital.specialty_service.dto.SpecialtyRequestDTO;
import com.hospital.specialty_service.dto.SpecialtyResponseDTO;
import com.hospital.specialty_service.model.Specialty;
import com.hospital.specialty_service.repository.SpecialtyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpecialtyService {

  private final SpecialtyRepository specialtyRepository;
  private final ModelMapper modelMapper;

  public SpecialtyResponseDTO createSpecialty(SpecialtyRequestDTO requestDTO) {
    if (specialtyRepository.findByName(requestDTO.getName()).isPresent()) {
      throw new IllegalArgumentException("Ya existe una especialidad con el nombre: " + requestDTO.getName());
    }

    Specialty specialty = modelMapper.map(requestDTO, Specialty.class);
    @SuppressWarnings("null")
    Specialty savedSpecialty = specialtyRepository.save(specialty);

    return modelMapper.map(savedSpecialty, SpecialtyResponseDTO.class);
  }

  public List<SpecialtyResponseDTO> findAllSpecialties() {
    return specialtyRepository.findAll().stream()
        .map(specialty -> modelMapper.map(specialty, SpecialtyResponseDTO.class))
        .collect(Collectors.toList());
  }

  public SpecialtyResponseDTO findSpecialtyById(Long id) {
    @SuppressWarnings("null")
    Specialty specialty = specialtyRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Especialidad no encontrada con ID: " + id));

    return modelMapper.map(specialty, SpecialtyResponseDTO.class);
  }

  @SuppressWarnings("null")
  public Set<Long> getExistingSpecialtyIds(Set<Long> requestedIds) {
    List<Specialty> existingSpecialties = specialtyRepository.findAllById(requestedIds);
    return existingSpecialties.stream()
        .filter(specialty -> specialty.getStatus() == null || specialty.getStatus())
        .map(Specialty::getId)
        .collect(Collectors.toSet());
  }

  public void disableSpecialty(Long id) {
    @SuppressWarnings("null")
    Specialty specialty = specialtyRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Especialidad no encontrada con ID: " + id));
    
    specialty.setStatus(false);
    specialtyRepository.save(specialty);
  }

  public void enableSpecialty(Long id) {
    @SuppressWarnings("null")
    Specialty specialty = specialtyRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Especialidad no encontrada con ID: " + id));
    
    specialty.setStatus(true);
    specialtyRepository.save(specialty);
  }
}