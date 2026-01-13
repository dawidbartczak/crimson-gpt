import { useState } from "react";
import { HttpStatusCode } from "axios";
import { useNavigate } from "react-router-dom";

import Chat from "../../model/Chat";
import ChatContext from "./ChatContext";
import ChatService from "../../api/services/ChatService";



const ChatProvider = ({ children }: { children: React.ReactNode }) => {
    const navigate = useNavigate();

    const [chats, setChats] = useState<Chat[]>([]);

    const createChat = async (title: string) => {
        const response = await ChatService.post(title);

        if (response.status == HttpStatusCode.Created) {
            setChats((prev) => [response.data, ...prev]);

            navigate(`/c/${response.data.id}`);
        }
    }

    const fetchChats = async () => {
        const response = await ChatService.get()

        if (response.status == HttpStatusCode.Ok) {
            setChats(response.data);
        }
    }

    const updateChat = async (chatId: string, title: string) => {
        const response = await ChatService.patch(chatId, title)

        if (response.status == HttpStatusCode.Ok) {
            setChats(prev => prev.map(chat => chat.id == chatId ? { ...chat, title } : chat ));
        }
    }

    const deleteChat = async (chatId: string) => {
        const response = await ChatService.remove(chatId)

        if (response.status == HttpStatusCode.Ok) {
            setChats(prev => prev.filter(chat => chat.id != chatId));
        }
    }

    return (
        <ChatContext.Provider value={{ chats, createChat, fetchChats, updateChat, deleteChat }}>
            {children}
        </ChatContext.Provider>
    );
};



export default ChatProvider;