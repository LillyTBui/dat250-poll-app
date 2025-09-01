package org.dat250.poll.domains;

import java.util.Objects;

public class VoteOption {
    private String caption;
    private Integer presentationOrder;

    public VoteOption() {
    }

    public VoteOption(String caption, Integer presentationOrder) {
        this.caption = caption;
        this.presentationOrder = presentationOrder;
    }

    public String getCaption() {
        return caption;
    }

    public Integer getPresentationOrder() {
        return presentationOrder;
    }

    public void setPresentationOrder(Integer presentationOrder) {
        this.presentationOrder = presentationOrder;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VoteOption that = (VoteOption) o;
        return Objects.equals(caption, that.caption) && Objects.equals(presentationOrder, that.presentationOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caption, presentationOrder);
    }
}
