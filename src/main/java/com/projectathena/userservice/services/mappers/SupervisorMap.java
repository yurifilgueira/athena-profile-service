package com.projectathena.userservice.services.mappers;

import com.projectathena.userservice.model.dto.SupervisorDTO;
import com.projectathena.userservice.model.entities.Supervisor;

public class SupervisorMap {

    public static SupervisorDTO toDto(Supervisor supervisor) {
        return new SupervisorDTO(supervisor.getId(), supervisor.getName(), supervisor.getEmail());
    }

    public static Supervisor toEntity(SupervisorDTO supervisorDTO) {
        String supervisorId = supervisorDTO.id() == null ? null : supervisorDTO.id();

        return new Supervisor(supervisorId, supervisorDTO.name(), supervisorDTO.email());
    }

}
