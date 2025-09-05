import {type SubmitHandler, useForm} from "react-hook-form";
import type {UserType} from "../interfaces/interfaces.ts";
import {useMutation} from "@tanstack/react-query";
import axios from "axios";
import {useNavigate} from "react-router-dom";

export default function Register() {
    const {
        register,
        handleSubmit,
        formState: {errors}
    } = useForm<UserType>();

    const mutation = useMutation({
        mutationFn: (formData: UserType) => {
            return axios.post("http://localhost:8080/api/v1/users", formData)
        },
    })

    const navigate = useNavigate();

    const onSubmit: SubmitHandler<UserType> = (formData) => {
        mutation.mutate(formData);
        if (mutation.isSuccess) {
            navigate(`/users/${mutation.data.data.id}`);
        }
    };

    return (<div className={"mt-10 p-3 flex flex-col justify-center items-center"}>
            <h1 className={"mb-4 text-2xl"}>Register</h1>
            <form onSubmit={handleSubmit(onSubmit)} className={"flex flex-col gap-3"}>
                <div className="flex flex-col gap-1">
                    <label className={"font-semibold"}>Username</label>
                    <input {...register("username", {required: true})} className={"border border-gray-300 rounded-md px-2 py-1"} />
                    {errors.username && (<span className={"text-red-500 text-sm"}>This field is required</span>)}
                </div>

                <div className="flex flex-col gap-1">
                    <label className={"font-semibold"}>Email</label>
                    <input type="email" {...register("email", {required: true})} className={"border border-gray-300 rounded-md px-2 py-1"} />
                    {errors.email && (<span className={"text-red-500 text-sm"}>This field is required</span>)}
                </div>

                <div className="flex flex-col gap-1">
                    <label className={"font-semibold"}>Password</label>
                    <input type="password" {...register("password", {required: true})} className={"border border-gray-300 rounded-md px-2 py-1"} />
                    {errors.password && (<span className={"text-red-500 text-sm"}>This field is required</span>)}
                </div>
                <button type="submit" className={"mt-4 rounded-md bg-indigo-600 px-3.5 py-2.5 text-sm font-semibold text-white shadow-xs hover:bg-indigo-500 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-400"}>Register</button>
            </form>
        </div>
    )
}