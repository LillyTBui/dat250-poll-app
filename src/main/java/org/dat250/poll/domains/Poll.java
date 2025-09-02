package org.dat250.poll.domains;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Poll {
    private String id;
    private String question;
    private Set<Vote> votes = new HashSet<>();
    private Set<VoteOption> voteOptions = new HashSet<>();
    private String creatorId;
    private String visibility;
    private Instant publishedAt;
    private Instant validUntil;

    public Poll() {
    }

    public Poll(String question, String id, String creatorId, Set<VoteOption> voteOptions, String visibility, Instant publishedAt, Instant validUntil) {
        this.question = question;
        this.id = id;
        this.creatorId = creatorId;
        this.voteOptions = voteOptions;
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

    public void addVote(Vote vote) {
        this.votes.add(vote);
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Poll poll = (Poll) o;
        return Objects.equals(id, poll.id) && Objects.equals(question, poll.question) && Objects.equals(votes, poll.votes) && Objects.equals(voteOptions, poll.voteOptions) && Objects.equals(creatorId, poll.creatorId) && Objects.equals(visibility, poll.visibility) && Objects.equals(publishedAt, poll.publishedAt) && Objects.equals(validUntil, poll.validUntil);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, votes, voteOptions, creatorId, visibility, publishedAt, validUntil);
    }

    public void removeVote(Vote vote) {
        this.votes.remove(vote);
    }
}
