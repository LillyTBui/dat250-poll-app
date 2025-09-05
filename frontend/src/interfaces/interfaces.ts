export interface VoteOptionType {
    caption: string;
    presentationOrder: number;
}

export interface VoteType {
    id: number;
    pollId: number;
    userId: number;
    voteOption: VoteOptionType;
    publishedAt: string;
}

export interface PollType {
    id: number;
    question: string;
    voteOptions: VoteOptionType[];
    creatorId: number;
    visibility: boolean;
    publishedAt: string;
    validUntil: string;
    votes: VoteType[];
}