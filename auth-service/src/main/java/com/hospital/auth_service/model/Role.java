package com.hospital.auth_service.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

  ADMIN(Set.of(
      Permission.USER_MANAGE,
      Permission.SCHEDULE_CREATE,
      Permission.MEDICATION_READ,
      Permission.ANALYSIS_TYPE_READ)),

  DOCTOR(Set.of(
      Permission.SCHEDULE_READ,
      Permission.PATIENT_READ,
      Permission.HISTORY_READ,
      Permission.PAYMENT_READ,
      Permission.ATTENTION_CREATE,
      Permission.ATTENTION_READ,
      Permission.HISTORY_UPDATE,
      Permission.MEDICATION_READ,
      Permission.RECIPE_CREATE,
      Permission.ANALYSIS_TYPE_READ,
      Permission.ANALYSIS_CREATE)),

  RECEPCIONISTA(Set.of(
      Permission.SCHEDULE_READ,
      Permission.SCHEDULE_CREATE,
      Permission.APPOINTMENT_READ,
      Permission.APPOINTMENT_CREATE,
      Permission.PATIENT_READ)),

  ENFERMERA(Set.of(
      Permission.PATIENT_CREATE,
      Permission.PATIENT_READ,
      Permission.HISTORY_CREATE)),

  CAJERO(Set.of(
      Permission.PATIENT_READ,
      Permission.APPOINTMENT_READ,
      Permission.PAYMENT_CREATE,
      Permission.PAYMENT_READ));

  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    authorities.addAll(
        permissions.stream()
            .map(p -> new SimpleGrantedAuthority(p.name()))
            .collect(Collectors.toList()));
    return authorities;
  }
}