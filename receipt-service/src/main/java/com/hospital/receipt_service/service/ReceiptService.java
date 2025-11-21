package com.hospital.receipt_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hospital.receipt_service.client.AppointmentServiceClient;
import com.hospital.receipt_service.client.EmployeeServiceClient;
import com.hospital.receipt_service.dto.EmployeeDTO;
import com.hospital.receipt_service.exception.CustomException;
import com.hospital.receipt_service.dto.MedicalAppointmentDTO;
import com.hospital.receipt_service.dto.ReceiptRequestDTO;
import com.hospital.receipt_service.dto.ReceiptResponseDTO;
// import com.hospital.receipt_service.dto.ReceiptUpdateDTO;
import com.hospital.receipt_service.model.Receipt;
import com.hospital.receipt_service.model.Status;
import com.hospital.receipt_service.repository.ReceiptRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceiptService {
  private final ReceiptRepository receiptRepository;
  private final EmployeeServiceClient employeeServiceClient;
  private final AppointmentServiceClient appointmentServiceClient;
  private final ModelMapper modelMapper;

  public ReceiptResponseDTO createReceipt(ReceiptRequestDTO dto) {
    EmployeeDTO employee = employeeServiceClient.getEmployeeByDni(dto.getEmployeeDni());
    if (employee == null) {
      throw new CustomException("Empleado no encontrado con DNI: " + dto.getEmployeeDni(), HttpStatus.NOT_FOUND);
    }
    if (Boolean.FALSE.equals(employee.getIsEnabled())) {
      throw new CustomException("El empleado está deshabilitado", HttpStatus.BAD_REQUEST);
    }

    MedicalAppointmentDTO appointment = appointmentServiceClient.getAppointmentById(dto.getAppointmentId());
    if (appointment == null) {
      throw new CustomException("Cita médica no encontrada con ID: " + dto.getAppointmentId(), HttpStatus.NOT_FOUND);
    }

    Receipt receipt = Receipt.builder()
        .employeeDni(dto.getEmployeeDni())
        .appointmentId(dto.getAppointmentId())
        .paymentMethod(dto.getPaymentMethod())
        .ruc(dto.getRuc())
        .companyName(dto.getCompanyName())
        .totalAmount(appointment.getFinalCost())
        .createdAt(LocalDateTime.now())
        .status(Status.PENDIENTE)
        .build();

    @SuppressWarnings("null")
    Receipt savedReceipt = receiptRepository.save(receipt);

    ReceiptResponseDTO response = modelMapper.map(savedReceipt, ReceiptResponseDTO.class);
    response.setEmployee(employee);
    response.setMedicalAppointment(appointment);

    return response;
  }

  public List<ReceiptResponseDTO> getAllReceipts() {
    List<Receipt> receipts = receiptRepository.findAll();
    return receipts.stream()
        .map(this::mapToResponseDTO)
        .collect(Collectors.toList());
  }

  public ReceiptResponseDTO getReceiptById(Long id) {
    @SuppressWarnings("null")
    Receipt receipt = receiptRepository.findById(id)
        .orElseThrow(() -> new CustomException("Boleta no encontrada con ID: " + id, HttpStatus.NOT_FOUND));
    return mapToResponseDTO(receipt);
  }

  public List<ReceiptResponseDTO> getReceiptsByAppointmentId(Long appointmentId) {
    List<Receipt> receipts = receiptRepository.findByAppointmentId(appointmentId);
    return receipts.stream()
        .map(this::mapToResponseDTO)
        .collect(Collectors.toList());
  }

  public List<ReceiptResponseDTO> getReceiptsByEmployeeDni(String employeeDni) {
    List<Receipt> receipts = receiptRepository.findByEmployeeDni(employeeDni);
    return receipts.stream()
        .map(this::mapToResponseDTO)
        .collect(Collectors.toList());
  }

  public List<ReceiptResponseDTO> getReceiptsByCreatedAtRange(LocalDateTime start, LocalDateTime end) {
    if (start.isAfter(end) || start.isEqual(end)) {
      throw new CustomException("La fecha/hora de inicio debe ser anterior a la de fin", HttpStatus.BAD_REQUEST);
    }
    List<Receipt> receipts = receiptRepository.findByCreatedAtBetween(start, end);
    return receipts.stream()
        .map(this::mapToResponseDTO)
        .collect(Collectors.toList());
  }

  public void updateReceiptStatus(Long id, Status status) {
    @SuppressWarnings("null")
    Receipt receipt = receiptRepository.findById(id)
        .orElseThrow(() -> new CustomException("Boleta no encontrada con ID: " + id, HttpStatus.NOT_FOUND));
    receipt.setStatus(status);
    receiptRepository.save(receipt);
  }

  // public ReceiptResponseDTO updateReceipt(Long id, ReceiptUpdateDTO dto) {
  //   @SuppressWarnings("null")
  //   Receipt receipt = receiptRepository.findById(id)
  //       .orElseThrow(() -> new IllegalArgumentException("Boleta no encontrada con ID: " + id));

  //   receipt.setPaymentMethod(dto.getPaymentMethod());
  //   receipt.setRuc(dto.getRuc());
  //   receipt.setCompanyName(dto.getCompanyName());

  //   Receipt updatedReceipt = receiptRepository.save(receipt);

  //   return mapToResponseDTO(updatedReceipt);
  // }

  private <T> T getServiceData(Supplier<T> serviceCall) {
    try {
      return serviceCall.get();
    } catch (Exception e) {
      return null;
    }
  }

  private ReceiptResponseDTO mapToResponseDTO(Receipt receipt) {
    ReceiptResponseDTO response = modelMapper.map(receipt, ReceiptResponseDTO.class);

    response.setEmployee(getServiceData(
        () -> employeeServiceClient.getEmployeeByDni(receipt.getEmployeeDni())));
    response.setMedicalAppointment(getServiceData(
        () -> appointmentServiceClient.getAppointmentById(receipt.getAppointmentId())));

    return response;
  }

}
