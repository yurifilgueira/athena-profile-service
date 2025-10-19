package com.projectathena.userservice.repositories;

import com.projectathena.userservice.model.entities.GitRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GitReposRepository extends JpaRepository<GitRepo,String> {
}
