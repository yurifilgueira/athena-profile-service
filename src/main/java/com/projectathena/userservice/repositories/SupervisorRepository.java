package com.projectathena.userservice.repositories;

import com.projectathena.userservice.model.entities.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisorRepository extends JpaRepository<Supervisor,String> {
}
