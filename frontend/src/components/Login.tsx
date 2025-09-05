import {useForm, type SubmitHandler} from "react-hook-form"
import {NavLink} from "react-router-dom";

interface LoginType{
    username: string,
    password: string,
}

export default function Login() {
    const {
        register,
        handleSubmit,
        formState: {errors}
    } = useForm<LoginType>();

    const onSubmit: SubmitHandler<LoginType> = (data) => console.log(data);

    return (<div className={"mt-10 p-3 flex flex-col justify-center items-center"}>
            <h1 className={"mb-4 text-2xl"}>Login</h1>
            <form onSubmit={handleSubmit(onSubmit)} className={"flex flex-col gap-3"}>
                <div className="flex flex-col gap-1">
                    <label className={"font-semibold"}>Username</label>
                    <input {...register("username", {required: true})} className={"border border-gray-300 rounded-md px-2 py-1"} />
                    {errors.username && (<span className={"text-red-500 text-sm"}>This field is required</span>)}
                </div>

                <div className="flex flex-col gap-1">
                    <label className={"font-semibold"}>Password</label>
                    <input type="password" {...register("password", {required: true})} className={"border border-gray-300 rounded-md px-2 py-1"} />
                    {errors.password && (<span className={"text-red-500 text-sm"}>This field is required</span>)}
                </div>
                <button type="submit" className={"mt-4 rounded-md bg-indigo-600 px-3.5 py-2.5 text-sm font-semibold text-white shadow-xs hover:bg-indigo-500 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-400"}>Login</button>
            </form>
            <p className={"mt-4 text-sm text-gray-500"}>Don't have an account? <NavLink to={"/register"} className={"font-semibold"}>Register</NavLink></p>
    </div>
    )
}