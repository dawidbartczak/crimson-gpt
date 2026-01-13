import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { HttpStatusCode } from "axios";

import AuthContext from "./AuthContext";
import AuthService from "../../api/services/AuthService"



const AuthProvider = ({ children }: { children: React.ReactNode }) => {
    const navigate = useNavigate();

    const [authenticated, setAuthenticated] = useState(localStorage.getItem("token") ? true : false);

    useEffect(() => { authenticate() }, []);

    const register = async (email: string, username: string, password: string) => {
        const response = await AuthService.register(email, username, password);

        if (response.status == HttpStatusCode.Created) {
            navigate("/login");
        }
    }

    const login = async (email: string, password: string) => {
        const response = await AuthService.login(email, password);

        if (response.status == HttpStatusCode.Ok) {
            localStorage.setItem("token", response.data.token)

            setAuthenticated(true);

            navigate("/")
        }
    }

    const authenticate = async () => {
        const response = await AuthService.authenticate();

        if (response.status == HttpStatusCode.Ok) {
            setAuthenticated(true);
        } else {
            setAuthenticated(false);
        }
    }

    const logout = async () => {
        localStorage.removeItem("token");

        setAuthenticated(false);
    }

    return (
        <AuthContext.Provider value={{ authenticated, register, login, logout, authenticate }}>
            {children}
        </AuthContext.Provider>
    );
};



export default AuthProvider