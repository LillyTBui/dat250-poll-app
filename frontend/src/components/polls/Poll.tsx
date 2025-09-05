import type {PollType} from "../../interfaces/interfaces.ts";

export default function Poll(poll: PollType) {
    function handleVote(number: number){
        console.log("voted on number: ", number);
    }
    return <div className="bg-indigo-200 p-5 max-w-sm rounded-lg text-center shadow-md">
        <h2 className={"text-lg font-bold"}>{poll.question}</h2>
        <ul className={"mt-8 flex flex-col gap-1"}>
            {poll.voteOptions.map(option => (
                <li key={option.presentationOrder} className={"bg-white hover:bg-gray-100"} onClick={() => handleVote(option.presentationOrder)}>{option.caption}</li>
            ))}
        </ul>
    </div>
}