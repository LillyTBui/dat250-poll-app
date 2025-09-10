import type {PollType, VoteType} from "../../interfaces/interfaces.ts";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import axios from "axios";
import {useState} from "react";

interface PollProps {
    poll: PollType;
    userId?: number;
}

export default function Poll({poll, userId}: PollProps) {
    const [hasVoted, setHasVoted] = useState(false);
    const [results, setResults] = useState(new Map());

    const queryClient = useQueryClient();

    const mutation = useMutation({
        mutationFn: (voteRequest: VoteType) => {
            return axios.post(`http://localhost:8080/api/v1/polls/${poll.id}/votes`, voteRequest)
        },
        onSuccess: () => {
            queryClient.invalidateQueries(['userData']);
            setHasVoted(true);
        }
    })
    function handleVote(question: string, number: number){
        const voteRequest = {
            userId: userId,
            voteOption: {
                caption: question,
                presentationOrder: number
            }
        }
        console.log(voteRequest);
        mutation.mutate(voteRequest);
    }

    function calculateResults(){
        const map = new Map();
        poll.voteOptions.forEach(voteOption => {
            map.set(voteOption.caption, 1);
        })
        // check each vote and update map
        poll.votes.forEach((vote) => {
            const caption = vote.voteOption.caption;
            const currentResult = map.get(caption);
            map.set(caption, currentResult + 1);
        })
        setResults(map);
    }

    if (hasVoted) {
        calculateResults();
        console.log(results);
    }

    console.log("THIS IS INSIDE POLL")
    console.log(poll);

    return <div className="bg-indigo-200 p-5 max-w-sm w-full rounded-lg text-center shadow-md">
        <h2 className={"text-lg font-bold"}>{poll.question}</h2>
        <ul className={"mt-8 flex flex-col gap-1"}>
            {poll.voteOptions.map(option => (
                <li key={option.presentationOrder} className={`hover:bg-gray-100 ${hasVoted ? "bg-pink-100" : "bg-white"}`} onClick={() => handleVote(option.caption, option.presentationOrder)}>{option.caption}</li>
            ))}
        </ul>
        {hasVoted && (
            <div className={"mt-4 text-left"}>
                <h3 className={"font-semibold"}>Voting results</h3>
            </div>
        )}
    </div>
}