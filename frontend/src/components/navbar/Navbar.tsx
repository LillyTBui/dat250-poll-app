import LinkComponent from "./LinkComponent.tsx";

export default function Navbar() {
    return <nav aria-label="main navigation" className={"sticky top-4 flex items-center justify-center"}>
        <div className="bg-indigo-50 flex gap-5 px-6 py-3 rounded-full">
            <LinkComponent path={"/"} title={"Home"} />
            <LinkComponent path={"/polls"} title={"Polls"} />
            <LinkComponent path={"/login"} title={"Login"} />
        </div>
    </nav>
}