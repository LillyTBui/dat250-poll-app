import type {PollType} from "../../interfaces/interfaces.ts";

export default function Poll(poll: PollType) {
    return <div className="bg-indigo-200 m-2  p-5 max-w-sm mx-auto rounded-lg text-center shadow-md">
        <h2 className={"text-lg font-bold"}>{poll.question}</h2>
        <ul className={"mt-8 flex flex-col gap-1"}>
            {poll.voteOptions.map(option => (
                <li className={"bg-white hover:bg-gray-100"}>{option.caption}</li>
            ))}
        </ul>
    </div>
}