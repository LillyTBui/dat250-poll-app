import {NavLink} from "react-router";

function Home(){
    return <div>
        <nav aria-label="main navigation" className={"sticky top-4 flex items-center justify-center"}>
            <div className="bg-indigo-50 flex gap-5 px-6 py-3 rounded-full">
                <NavLink to="/polls">Polls</NavLink>
                <NavLink to="/login">Login</NavLink>
            </div>
        </nav>
        <header className={"h-screen flex items-center justify-center bg-white px-2"}>
            <div className="text-center flex flex-col justify-center items-center">
                <h1 className="text-5xl font-semibold text-gray-900 sm:text-7xl">
                    Poll App
                </h1>
                <p className="mt-3 text-lg font-medium text-gray-500 sm:text-xl">Easily create customized polls and get instant feedbacks!</p>
                <a href="#"
                   className="mt-4 rounded-md bg-indigo-600 px-3.5 py-2.5 text-sm font-semibold text-white shadow-xs hover:bg-indigo-500 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-400">
                    Get started
                </a>
            </div>
        </header>
    </div>
}




export default Home;
