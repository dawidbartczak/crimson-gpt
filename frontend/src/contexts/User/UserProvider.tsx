import { useEffect, useState } from "react";
import { HttpStatusCode } from "axios";

import UserService from "../../api/services/UsersService";
import UserContext from "./UserContext";
import { useAuth } from "../Auth";



const UserProvider = ({ children }: { children: React.ReactNode }) => {
    const { authenticated } = useAuth();

    const [username, setUsers] = useState("");

    useEffect(() => { fetchUsername() }, [authenticated])
    
    const fetchUsername = async () => {
        const response = await UserService.get();

        if (response.status == HttpStatusCode.Ok) {
            setUsers(response.data)
        }
    }

    return (
        <UserContext.Provider value={{ username, fetchUsername }}>
            {children}
        </UserContext.Provider>
    );
};



export default UserProvider;