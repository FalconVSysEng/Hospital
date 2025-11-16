package com.hospital.office_service.model;

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
@Table(name = "office")
@NoArgsConstructor
public class Office {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  private String address;
  private Integer floor;
  private String officeNumber;

  private Boolean status;

  @Builder.Default
  @ElementCollection
  @CollectionTable(name = "office_specialty_ids", joinColumns = @JoinColumn(name = "office_id"))
  @Column(name = "specialty_id")
  private Set<Long> specialtyIds = new HashSet<>();
}