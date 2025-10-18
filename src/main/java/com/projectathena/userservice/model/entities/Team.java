package com.projectathena.userservice.model.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Table(name = "teams")
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(length = 100)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private Supervisor supervisor;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Developer> developers;

    public Team() {
    }

    public Team(String id, String name, Supervisor supervisor, List<Developer> developers) {
        this.id = id;
        this.name = name;
        this.supervisor = supervisor;
        this.developers = developers;
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

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
