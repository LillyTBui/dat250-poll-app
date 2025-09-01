package org.dat250.poll.domains;

import java.time.Instant;

public class Vote {
    private String id;
    private String pollId;
    private String userId;
    private VoteOption voteOption;
    private Instant publishedAt;
    private boolean isValid;

    public Vote() {
    }

    public Vote(String pollId, String userId, VoteOption voteOption, Instant publishedAt) {
        this.pollId = pollId;
        this.userId = userId;
        this.voteOption = voteOption;
        this.publishedAt = publishedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public VoteOption getVoteOption() {
        return voteOption;
    }

    public void setVoteOption(VoteOption voteOption) {
        this.voteOption = voteOption;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
