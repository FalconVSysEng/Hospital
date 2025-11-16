package com.hospital.office_service.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.hospital.office_service.client.SpecialtyServiceClient;
import com.hospital.office_service.dto.OfficeRequestDTO;
import com.hospital.office_service.dto.OfficeResponseDTO;
import com.hospital.office_service.model.Office;
import com.hospital.office_service.repository.OfficeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OfficeService {
  private final ModelMapper modelMapper;
  private final OfficeRepository officeRepository;
  private final SpecialtyServiceClient specialtyServiceClient;

  public OfficeResponseDTO createOffice(OfficeRequestDTO dto) {
    if (dto.getSpecialtyIds() != null && !dto.getSpecialtyIds().isEmpty()) {
      Set<Long> existingSpecialtyIds = specialtyServiceClient.getExistingSpecialtyIds(dto.getSpecialtyIds());

      if (!existingSpecialtyIds.equals(dto.getSpecialtyIds())) {
        Set<Long> invalidIds = dto.getSpecialtyIds().stream()
            .filter(id -> !existingSpecialtyIds.contains(id))
            .collect(Collectors.toSet());
        throw new IllegalArgumentException(
            "Las siguientes especialidades no existen o están deshabilitadas: " + invalidIds);
      }
    }

    Office office = modelMapper.map(dto, Office.class);
    office.setStatus(true);

    Office savedOffice = officeRepository.save(office);
    return modelMapper.map(savedOffice, OfficeResponseDTO.class);
  }

  @Transactional
  public List<OfficeResponseDTO> getAllOffices() {
    return officeRepository.findAll().stream()
        .map(office -> modelMapper.map(office, OfficeResponseDTO.class))
        .collect(Collectors.toList());
  }

  @Transactional
  @SuppressWarnings("null")
  public OfficeResponseDTO getOfficeById(Long id) {
    Office office = officeRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Consultorio no encontrado con ID: " + id));

    return modelMapper.map(office, OfficeResponseDTO.class);
  }

  @SuppressWarnings("null")
  public void disableOffice(Long id) {
    Office office = officeRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Consultorio no encontrado con ID: " + id));

    office.setStatus(false);
    officeRepository.save(office);
  }

  @SuppressWarnings("null")
  public void enableOffice(Long id) {
    Office office = officeRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Consultorio no encontrado con ID: " + id));

    office.setStatus(true);
    officeRepository.save(office);
  }
}
