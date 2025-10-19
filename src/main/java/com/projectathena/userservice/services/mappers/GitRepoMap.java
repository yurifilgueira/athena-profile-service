package com.projectathena.userservice.services.mappers;

import com.projectathena.userservice.model.dto.GitRepoDTO;
import com.projectathena.userservice.model.dto.SupervisorDTO;
import com.projectathena.userservice.model.entities.GitRepo;
import com.projectathena.userservice.model.entities.Supervisor;

public class GitRepoMap {

    public static GitRepoDTO toDto(GitRepo gitRepo) {
        return new GitRepoDTO(gitRepo.getId(), gitRepo.getName(), gitRepo.getOwner(), gitRepo.getUrl());
    }

    public static GitRepo toEntity(GitRepoDTO gitRepoDTO) {
        String gitRepoId = gitRepoDTO.id() == null ? null : gitRepoDTO.id();

        return new GitRepo(gitRepoId, gitRepoDTO.name(), gitRepoDTO.owner(), gitRepoDTO.url());
    }

}
