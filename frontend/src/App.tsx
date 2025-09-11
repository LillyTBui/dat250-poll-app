import { Routes, Route} from "react-router-dom";
import Home from "./components/Home.tsx";
import Polls from "./components/polls/Polls.tsx";
import Navbar from "./components/navbar/Navbar.tsx";
import Login from "./components/Login.tsx";
import Register from "./components/Register.tsx";
import User from "./components/User.tsx";

function App() {
  return (
    <>
        <Navbar />
        <Routes>
            <Route path={"/"} element={<Home/>}/>
            <Route path={"/polls"} element={<Polls/>} />
            <Route path={"/login"} element={<Login/>}/>
            <Route path={"/register"} element={<Register/>} />
            <Route path={"/users/:id"} element={<User/>} />
        </Routes>
    </>
  )
}

export default App
