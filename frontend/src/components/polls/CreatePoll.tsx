import {type SubmitHandler, useForm, useFieldArray} from "react-hook-form";
import type {PollType, VoteOptionType} from "../../interfaces/interfaces.ts";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import axios from "axios";

export default function CreatePoll({creatorId, onClose} : {creatorId: number, onClose: () => void}) {
    const {
        control,
        register,
        handleSubmit,
        formState: {errors}
    } = useForm<PollType>({
            defaultValues: {
                voteOptions: [
                    {
                        caption: "",
                        presentationOrder:0
                    },
                    {
                        caption: "",
                        presentationOrder:1
                    }
                ]
            }
        }
    );

    const {fields, append, remove} = useFieldArray({
        control,
        name: "voteOptions",
    })

    const queryClient = useQueryClient();

    function addVoteOption() {
        append({
            caption: "",
            presentationOrder: fields.length
        })
    }

    function removeVoteOption(index: number){
        remove(index);
    }

    const mutation = useMutation({
        mutationFn: (formData: PollType) => {
            return axios.post("http://localhost:8080/api/v1/polls", formData)
        },
        onSuccess: () => {
            queryClient.invalidateQueries(['userData']);
            onClose();
        }
    })

    const onSubmit: SubmitHandler<PollType> = (formData) => {
        // correct the presentationOrder based on the index to avoid duplicated values
        let correctVoteOptions: VoteOptionType[] = []
        formData.voteOptions.forEach((voteOption, index) => {
            let option = {
                caption: voteOption.caption,
                presentationOrder: index
            }
            correctVoteOptions.push(option)
        })
        formData.voteOptions = correctVoteOptions;

        const createRequest : PollType = {
            creatorId: creatorId,
            ...formData
        }
        mutation.mutate(createRequest);
    };

    return <div className={"fixed inset-0"}>
        <div className="absolute inset-0 bg-black opacity-50">
        </div>
        <div className={"p-10 bg-gray-300 max-w-sm mx-auto relative top-1/8 max-h-[80vh] overflow-auto"}>
            <button className={"absolute right-4 top-2"} onClick={onClose}>X</button>
            <div className="flex flex-col justify-center items-center relative">
                <h1 className={"text-xl font-semibold"}>Create poll</h1>

                <form onSubmit={handleSubmit(onSubmit)} className={"flex flex-col gap-3 mt-4"}>
                    <div className="flex flex-col gap-1">
                        <label className={"font-semibold"}>Question</label>
                        <input {...register("question", {required: true})} className={"bg-white border border-gray-300 rounded-md px-2 py-1"} />
                        {errors.question && (<span className={"text-red-500 text-sm"}>This field is required</span>)}
                    </div>

                    {fields.map((_, index) => (
                        <div key={index} className={"bg-purple-100 flex flex-col gap-3 p-2 rounded-md"}>
                            <div className="flex justify-between items-baseline curser-pointer">
                                <label className={"text-md font-semibold"}>Vote option {index + 1}</label>
                                {fields.length !== 2 && (
                                    <button type="button" className={"text-gray-500 text-sm"} onClick={() => removeVoteOption(index)}>Remove</button>
                                )}
                            </div>

                            <input key={index} className={"bg-white rounded-md px-2 py-1"} {...register(`voteOptions.${index}.caption`, {required: true})} />
                            {errors.voteOptions?.[index]?.caption && (
                                <span className="text-red-500 text-sm">This field is required</span>
                            )}
                        </div>
                    ))}
                    <button type="button" className={"text-left hover:font-semibold"} onClick={() => addVoteOption()}>Add new option +</button>

                    <button type="submit" className={"mt-4 rounded-md bg-black px-3.5 py-2.5 text-sm font-semibold text-white shadow-xs hover:bg-gray-500 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-black"}>Create new poll</button>
                </form>
            </div>
            </div>
    </div>
}