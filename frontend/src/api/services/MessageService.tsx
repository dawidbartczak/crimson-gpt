import AxiosInstance from "../AxiosInstance";



const get = async (chatId: string) => {
    const response = await AxiosInstance.get(`/chats/${chatId}/messages`);

    return response;
}



export default { get }