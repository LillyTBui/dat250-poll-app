package org.dat250.poll.domains;

import java.time.Instant;

public class Vote {
    private String id;
    private User user;
    private Instant publishedAt;
    private boolean isValid;

    public Vote() {
    }

    public Vote(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
