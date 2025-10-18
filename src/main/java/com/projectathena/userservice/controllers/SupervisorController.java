package com.projectathena.userservice.controllers;

import com.projectathena.userservice.model.dto.SupervisorDTO;
import com.projectathena.userservice.services.SupervisorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/supervisors")
public class SupervisorController {

    private final SupervisorService supervisorService;

    public SupervisorController(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){

        var supervisor = supervisorService.findById(id);

        if (supervisor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(supervisorService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(SupervisorDTO supervisorDTO){
        return ResponseEntity.ok(supervisorService.create(supervisorDTO));
    }
}
