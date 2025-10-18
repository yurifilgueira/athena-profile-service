package com.projectathena.userservice.model.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Table(name = "supervisors")
@Entity
public class Supervisor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;

    public Supervisor() {
    }

    public Supervisor(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Supervisor that = (Supervisor) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
