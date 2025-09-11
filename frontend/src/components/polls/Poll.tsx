import type {PollType, VoteType} from "../../interfaces/interfaces.ts";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import axios from "axios";
import {useEffect, useState} from "react";

interface PollProps {
    poll: PollType;
    userId?: number;
}

export default function Poll({poll, userId}: PollProps) {
    const [showResults, setShowResults] = useState(false);
    const [results, setResults] = useState(new Map());

    const queryClient = useQueryClient();

    // mutation for when the user has not voted before
    const createMutation = useMutation({
        mutationFn: (voteRequest: VoteType) => {
            return axios.post(`http://localhost:8080/api/v1/polls/${poll.id}/votes`, voteRequest)
        },
        onSuccess: () => {
            queryClient.invalidateQueries({
                queryKey: ['userData']
            });
        }
    })

    // mutation for when the user updates a vote
    const updateMutation = useMutation({
        mutationFn: (voteRequest: VoteType) => {
            return axios.put(`http://localhost:8080/api/v1/polls/${voteRequest.pollId}/votes/${voteRequest.id}`, voteRequest)
        },
        onSuccess: () => {
            queryClient.invalidateQueries({
                queryKey: ['userData']
            });
        }
    })

    function handleVote(caption: string, number: number){
        const voteRequest: VoteType = {
            userId: userId,
            voteOption: {
                caption: caption,
                presentationOrder: number
            }
        }
        const oldVote = getVote();
        if (oldVote != undefined){
            const updatedVote = {
                ...oldVote,
                voteOption: voteRequest.voteOption
            }
            updateMutation.mutate(updatedVote);
        } else {
            createMutation.mutate(voteRequest);
        }
    }

    function calculateResults(){
        const map = new Map();
        // create a map entry for each vote option
        poll.voteOptions.forEach(voteOption => {
            map.set(voteOption.caption, 0);
        })
        // check each vote and update map
        poll.votes.forEach((vote) => {
            const caption = vote.voteOption.caption;
            const currentResult = map.get(caption);
            map.set(caption, currentResult + 1);
        })
        setResults(map);
    }

    function getVote(): VoteType | undefined {
        // get vote if user has already voted
        return poll.votes.find(vote => vote.userId == userId);
    }

    // everytime someone has voted we want to update the results
    useEffect(() => {
        calculateResults();
    }, [poll.votes])

    return <div className="bg-indigo-200 p-5 max-w-sm w-full rounded-lg text-center shadow-md">
        <h2 className={"text-lg font-bold"}>{poll.question}</h2>
        <ul className={"mt-8 flex flex-col gap-1"}>
            {poll.voteOptions.map(option => (
                <li key={option.presentationOrder} className={`hover:bg-gray-100 bg-white rounded-sm`} onClick={() => handleVote(option.caption, option.presentationOrder)}>{option.caption}</li>
            ))}
        </ul>
        {!showResults ?
            <button className={"my-4 py-2 px-3 bg-black text-white rounded-md"} onClick={() => setShowResults(true)}>Show results</button> :
            <button className={"my-4 py-2 px-3 bg-gray-400 text-white rounded-md"} onClick={() => setShowResults(false)}>Hide results</button>
        }
        {showResults && (
            <div className={"text-left"}>
                <h3 className={"font-semibold uppercase"}>Voting results</h3>
                <ul className={"mt-2 flex flex-col gap-1"}>
                    {[...results.entries()].map(([key, value]) => (
                        <li key={key}>
                            {key}: {value} votes
                        </li>
                    ))}
                </ul>
            </div>
        )}
    </div>
}