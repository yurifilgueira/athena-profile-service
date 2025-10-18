package com.projectathena.userservice.services;

import com.projectathena.userservice.model.dto.SupervisorDTO;
import com.projectathena.userservice.model.entities.Supervisor;
import com.projectathena.userservice.repositories.SupervisorRepository;
import com.projectathena.userservice.services.mappers.SupervisorMap;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;

    public SupervisorService(SupervisorRepository supervisorRepository) {
        this.supervisorRepository = supervisorRepository;
    }

    public Optional<Supervisor> findById(String id) {
        return supervisorRepository.findById(id);
    }

    public Supervisor create(SupervisorDTO supervisorDTO) {
        Supervisor supervisor = SupervisorMap.toEntity(supervisorDTO);
        return supervisorRepository.save(supervisor);
    }

}