import {NavLink} from "react-router";

export default function LinkComponent({path, title} : {path:string, title:string}){
    return (
        <NavLink to={path} className={({isActive}) =>
        isActive ? "font-semibold" : "hover:font-semibold"}>
            {title}
        </NavLink>
    )
}