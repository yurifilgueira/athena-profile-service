package com.projectathena.userservice.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public record SupervisorDTO(
        String id,
        String name,
        String email
){
}
