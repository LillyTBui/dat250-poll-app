import {useQuery} from "@tanstack/react-query";
import type {PollType} from "../../interfaces/interfaces.ts";
import Poll from "./Poll.tsx";

function Polls(){
    const {data} = useQuery({
        queryKey: ['polls'],
        queryFn: async () => {
            const response = await fetch("http://localhost:8080/api/v1/polls");
            if (!response.ok) {
                throw new Error("Failed to fetch Polls");
            }
            return response.json();
        }
    });

    console.log(data);

    return <section>
        <div className="text-center mt-15 mb-8">
            <h1 className={"text-2xl font-semibold mb-2"}>Public polls</h1>
            <p>Explore all public polls and vote on any you want!</p>
        </div>
        {data?.length == 0 ? (
            <p className={"text-center"}>No polls found... 😭</p>
        ) : data?.map((poll: PollType) => (
            <Poll key={poll.id} {...poll} />
        ))}
    </section>
}

export default Polls;