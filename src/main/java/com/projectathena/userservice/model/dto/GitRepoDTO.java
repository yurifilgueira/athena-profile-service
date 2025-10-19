package com.projectathena.userservice.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public record GitRepoDTO(
     String id,
     String name,
     String owner,
     String url
) {
}
