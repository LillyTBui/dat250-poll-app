import {useQuery} from "@tanstack/react-query";
import {useParams} from "react-router-dom";
import type {PollType} from "../interfaces/interfaces.ts";
import Poll from "./polls/Poll.tsx";

export default function User(){
    const {id} = useParams();

    const { data } = useQuery({
        queryKey: ['userData'],
        queryFn: async () => {
            const response = await fetch(`http://localhost:8080/api/v1/users/${id}`);
            if(!response.ok){
                throw new Error("Failed to fetch user data");
            }
            return response.json();
        }
    })

    console.log(data)

    return <div className="mt-15 px-5">
        <h1 className={"text-xl font-semibold"}>Welcome {data?.username}!</h1>
        <div className={"my-8"}>
            <div className="flex gap-4 align-center">
                <h2 className={"text-lg font-semibold"}>My polls</h2>
                <button className={"bg-pink-200 py-1 px-2 rounded-md hover:bg-pink-100 focus-visible:outline-2"}>Create new poll</button>
            </div>
            <div className="mt-5 flex gap-4 flew-wrap">
            {data?.polls?.map((poll: PollType) => (
                <Poll key={poll.id} {...poll} />
            ))}
            </div>    
        </div>
    </div>
}