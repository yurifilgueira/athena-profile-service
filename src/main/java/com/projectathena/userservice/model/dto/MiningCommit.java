package com.projectathena.userservice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

public class MiningCommit implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private MiningResult miningResult;
    private String oid;
    private String message;
    private String messageBody;
    private Integer additions;
    private Integer deletions;
    private GitAuthor author;
    private GitAuthor committer;
    private Boolean authoredByCommitter;
    private String commitUrl;
    private Instant committedDate;

    public MiningCommit() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MiningResult getMiningResult() {
        return miningResult;
    }

    public void setMiningResult(MiningResult miningResult) {
        this.miningResult = miningResult;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Integer getAdditions() {
        return additions;
    }

    public void setAdditions(Integer additions) {
        this.additions = additions;
    }

    public Integer getDeletions() {
        return deletions;
    }

    public void setDeletions(Integer deletions) {
        this.deletions = deletions;
    }

    public GitAuthor getAuthor() {
        return author;
    }

    public void setAuthor(GitAuthor author) {
        this.author = author;
    }

    public GitAuthor getCommitter() {
        return committer;
    }

    public void setCommitter(GitAuthor committer) {
        this.committer = committer;
    }

    public Boolean getAuthoredByCommitter() {
        return authoredByCommitter;
    }

    public void setAuthoredByCommitter(Boolean authoredByCommitter) {
        this.authoredByCommitter = authoredByCommitter;
    }

    public String getCommitUrl() {
        return commitUrl;
    }

    public void setCommitUrl(String commitUrl) {
        this.commitUrl = commitUrl;
    }

    public Instant getCommittedDate() {
        return committedDate;
    }

    public void setCommittedDate(Instant committedDate) {
        this.committedDate = committedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MiningCommit that = (MiningCommit) o;
        return Objects.equals(oid, that.oid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(oid);
    }
}
