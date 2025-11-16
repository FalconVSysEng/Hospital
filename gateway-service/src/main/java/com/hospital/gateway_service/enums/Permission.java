package com.hospital.gateway_service.enums;

public enum Permission {
  USER_MANAGE, // Crear, modificar y eliminar otros usuarios/roles

  SCHEDULE_READ, // Consultar disponibilidad y programación de horarios (Doctor/Recepcionista)
  SCHEDULE_CREATE, // Elaborar programación de horarios (Recepcionista)
  APPOINTMENT_READ, // Consultar citas existentes (Todos los que las necesiten)
  APPOINTMENT_CREATE, // Elaborar/Solicitar cita (Recepcionista)

  PATIENT_READ, // Consultar datos del paciente (Enfermera/Doctor/Recepcionista/Cajero)
  PATIENT_CREATE, // Registrar datos personales del paciente (Enfermera)
  HISTORY_READ, // Consultar Historia Médica completa (Doctor)
  HISTORY_CREATE, // Elaborar Historia Médica (Enfermera)
  HISTORY_UPDATE, // Añadir datos de atención a la historia (Doctor)

  PAYMENT_CREATE, // Elaborar boleta de venta / Registrar pago (Cajero)
  PAYMENT_READ, // Verificar el pago de la cita (Doctor/Cajero)

  ATTENTION_CREATE, // Elaborar/Registrar Atención Médica (Doctor)
  ATTENTION_READ, // Consultar datos de atención (Doctor/Recep.)

  MEDICATION_READ, // Consultar listado de medicamentos (Doctor/Admin)
  RECIPE_CREATE, // Elaborar Receta Médica (Doctor)
  ANALYSIS_TYPE_READ, // Consultar listado de tipos de análisis (Doctor/Admin)
  ANALYSIS_CREATE // Elaborar Solicitud de Análisis Clínico (Doctor)
}