package org.dat250.poll.domains;

import java.time.Instant;
import java.util.Set;

public class Poll {
    private String id;
    private String question;
    private Set<Vote> votes;
    private Set<VoteOption> voteOptions;

    private String creatorId;
    private String visibility;
    private Instant publishedAt;
    private Instant validUntil;

    public Poll() {
    }

    public Poll(String question, String id, String creatorId, String visibility, Instant publishedAt, Instant validUntil) {
        this.question = question;
        this.id = id;
        this.creatorId = creatorId;
        this.visibility = visibility;
        this.publishedAt = publishedAt;
        this.validUntil = validUntil;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public Set<VoteOption> getVoteOptions() {
        return voteOptions;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public void setVoteOptions(Set<VoteOption> voteOptions) {
        this.voteOptions = voteOptions;
    }

    public void setCreator(String creator) {
        this.creatorId = creator;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }
}
