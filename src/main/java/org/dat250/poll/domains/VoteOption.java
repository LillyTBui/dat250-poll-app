package org.dat250.poll.domains;

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
}
