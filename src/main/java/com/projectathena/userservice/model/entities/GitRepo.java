package com.projectathena.userservice.model.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Table(name = "git_repositories")
@Entity
public class GitRepo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 100, nullable = false)
    private String owner;
    @Column(nullable = false)
    private String url;

    public GitRepo() {
    }

    public GitRepo(String id, String name, String owner, String url) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.url = url;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GitRepo that = (GitRepo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
