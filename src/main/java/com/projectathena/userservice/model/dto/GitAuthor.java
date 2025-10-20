package com.projectathena.userservice.model.dto;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class GitAuthor implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String avatarUrl;
    private Date date;
    private String email;
    private String name;
    private String userId;
    private String login;

    public GitAuthor() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GitAuthor gitAuthor = (GitAuthor) o;
        return Objects.equals(id, gitAuthor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
