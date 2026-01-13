import AxiosInstance from "../AxiosInstance";



const register = async (email: string, username: string, password: string) => {
    const response = await AxiosInstance.post("/auth/register", { email, username, password });

    return response
}

const login = async (email: string, password: string) => {
    const response = await AxiosInstance.post("/auth/login", { email, password });

    return response
}

const authenticate = async () => {
    const response = await AxiosInstance.get("/auth/authenticate");

    return response
}



export default { register, login, authenticate }