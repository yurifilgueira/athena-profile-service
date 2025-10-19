package com.projectathena.userservice.controllers;

import com.projectathena.userservice.model.dto.GitRepoDTO;
import com.projectathena.userservice.model.dto.SupervisorDTO;
import com.projectathena.userservice.services.GitRepoService;
import com.projectathena.userservice.services.SupervisorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/git-repos")
public class GitRepoController {

    private final GitRepoService gitRepoService;

    public GitRepoController(GitRepoService gitRepoService) {
        this.gitRepoService = gitRepoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        var gitRepo = gitRepoService.findById(id);
        if (gitRepo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(gitRepoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(GitRepoDTO gitRepoDTO){
        return ResponseEntity.ok(gitRepoService.create(gitRepoDTO));
    }
}
