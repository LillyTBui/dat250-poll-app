import type {PollType, VoteType} from "../../interfaces/interfaces.ts";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import axios from "axios";

interface PollProps {
    poll: PollType;
    userId?: number;
}

export default function Poll({poll, userId}: PollProps) {
    const queryClient = useQueryClient();

    const mutation = useMutation({
        mutationFn: (voteRequest: VoteType) => {
            return axios.post(`http://localhost:8080/api/v1/polls/${poll.id}/votes`, voteRequest)
        },
        onSuccess: () => {
            queryClient.invalidateQueries(['userData']);
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

    return <div className="bg-indigo-200 p-5 max-w-sm w-full rounded-lg text-center shadow-md">
        <h2 className={"text-lg font-bold"}>{poll.question}</h2>
        <ul className={"mt-8 flex flex-col gap-1"}>
            {poll.voteOptions.map(option => (
                <li key={option.presentationOrder} className={"bg-white hover:bg-gray-100"} onClick={() => handleVote(option.caption, option.presentationOrder)}>{option.caption}</li>
            ))}
        </ul>
    </div>
}