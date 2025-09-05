import {NavLink} from "react-router-dom";


function Home(){
    return <div>
        <header className={"h-110 flex items-center justify-center bg-white px-2"}>
            <div className="text-center flex flex-col justify-center items-center">
                <h1 className="text-5xl font-semibold text-gray-900 sm:text-7xl">
                    Poll App
                </h1>
                <p className="mt-3 text-lg font-medium text-gray-500 sm:text-xl">Easily create customized polls and get instant feedbacks!</p>
                <NavLink to="/login"
                   className="mt-4 rounded-md bg-indigo-600 px-3.5 py-2.5 text-sm font-semibold text-white shadow-xs hover:bg-indigo-500 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-400">
                    Get started
                </NavLink>
            </div>
        </header>
    </div>
}




export default Home;
