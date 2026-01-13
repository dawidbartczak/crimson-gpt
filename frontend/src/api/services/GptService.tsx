import AxiosInstance from "../AxiosInstance";



const generate = async (context: string, chatId?: string) => {
    const response = await AxiosInstance.post("/model", { context, ...(chatId && { chatId }) });

    return response
}



export default { generate }