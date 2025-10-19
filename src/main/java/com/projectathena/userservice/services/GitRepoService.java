package com.projectathena.userservice.services;

import com.projectathena.userservice.model.dto.GitRepoDTO;
import com.projectathena.userservice.model.dto.SupervisorDTO;
import com.projectathena.userservice.model.entities.GitRepo;
import com.projectathena.userservice.model.entities.Supervisor;
import com.projectathena.userservice.repositories.GitReposRepository;
import com.projectathena.userservice.repositories.SupervisorRepository;
import com.projectathena.userservice.services.mappers.GitRepoMap;
import com.projectathena.userservice.services.mappers.SupervisorMap;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GitRepoService {

    private final GitReposRepository gitReposRepository;

    public GitRepoService(GitReposRepository gitReposRepository) {
        this.gitReposRepository = gitReposRepository;
    }

    public Optional<GitRepo> findById(String id) {
        return gitReposRepository.findById(id);
    }

    public GitRepo create(GitRepoDTO gitRepoDTO) {
        GitRepo gitRepo = GitRepoMap.toEntity(gitRepoDTO);
        return gitReposRepository.save(gitRepo);
    }

}