import {useQuery} from "@tanstack/react-query";
import {useParams} from "react-router-dom";
import type {PollType} from "../interfaces/interfaces.ts";
import Poll from "./polls/Poll.tsx";
import CreatePoll from "./polls/CreatePoll.tsx";
import {useState} from "react";

export default function User(){
    const {id} = useParams();
    const [createPoll, setCreatePoll] = useState(false);

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

    return <div className="mt-15 px-3 md:px-10 max-w-7xl mx-auto">
        {data != undefined  ? (<>
                <h1 className={"text-xl font-semibold"}>Welcome {data?.username}!👋🏻</h1>
                <div className={"my-8"}>
                    <div className="flex gap-4 align-center">
                        <h2 className={"text-lg font-semibold"}>My polls</h2>
                        <button className={"bg-pink-200 py-1 px-2 rounded-md hover:bg-pink-100 focus-visible:outline-2"}
                                onClick={() => setCreatePoll(true)}>Create new poll</button>
                    </div>
                    {createPoll && (
                        <CreatePoll creatorId={data.id} onClose={() => setCreatePoll(false)}/>
                    )}
                    <div className="mt-5 flex gap-5 flex-wrap">
                        {data?.polls?.map((poll: PollType) => (
                            <Poll key={poll.id} poll={poll} userId={id}/>
                        ))}
                    </div>
                </div>
            </>
        ):
        <p>Could not get user ☠️</p>
        }
    </div>
}