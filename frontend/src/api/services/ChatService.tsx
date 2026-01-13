import AxiosInstance from "../AxiosInstance";



const get = async () => {
    const response = await AxiosInstance.get("/chats");

    return response;
}

const post = async (title: string) => {
    const response = await AxiosInstance.post("/chats", { title });

    return response
}

const patch = async (chatId: string, title: string) => {
    const response = await AxiosInstance.patch(`/chats/${chatId}`, { title });

    return response
}

const remove = async (chatId: string) => {
    const response = await AxiosInstance.delete(`/chats/${chatId}`);

    return response
}




export default { get, post, patch, remove }