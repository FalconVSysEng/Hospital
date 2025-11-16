package com.hospital.doctor_service.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@Builder
@Table(name = "doctor")
@NoArgsConstructor
public class Doctor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, length = 8)
  private String dni;

  @Column(unique = true)
  private String codigo;

  @Builder.Default
  @ElementCollection(fetch = jakarta.persistence.FetchType.EAGER)
  @CollectionTable(name = "doctor_specialty_ids", joinColumns = @JoinColumn(name = "doctor_id"))
  @Column(name = "specialty_id")
  private Set<Long> specialtyIds = new HashSet<>();
}
