import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { HttpStatusCode } from "axios";

import Message from "../../model/Message";
import MessageContext from "./MessageContext";
import GptService from "../../api/services/GptService";
import MessageService from "../../api/services/MessageService";



const MessageProvider = ({ children }: { children: React.ReactNode }) => {
    const navigate = useNavigate();

    const [messages, setMessages] = useState<Message[]>([]);
    const [messagesBlocked, setMessagesBlocked] = useState(false);

    const sendMessage = async (context: string, chatId?: string) => {
        if (context.trim().length < 1) return;

        if (messagesBlocked) return;

        setMessagesBlocked(true);

        const response = await GptService.generate(context, chatId);

        switch (response.status) {
            case HttpStatusCode.Ok: {
                setMessages(prev => {
                    return [
                        ...(prev.map((message) => { return { ...message, animate: false } })),
                        { author: 0, content: context, animate: false },
                        { author: 1, content: response.data.prediction, animate: true }]
                });

                break;
            }

            case HttpStatusCode.Created: {
                setMessages(prev => {
                    return [
                        ...(prev.map((message) => { return { ...message, animate: false } })),
                        { author: 0, content: context, animate: false },
                        { author: 1, content: response.data.prediction, animate: true }
                    ]
                });

                navigate(`/c/${response.data.chatId}`)

                break;
            }
        }
    }

    const fetchMessages = async (chatId: string) => {
        const response = await MessageService.get(chatId);

        if (response.status == HttpStatusCode.Ok) {
            setMessages(response.data)
        }
    }

    const clearMessages = async () => {
        setMessages([]);
    }

    const lockMessages = () => {
        setMessagesBlocked(true);
    }

    const unlockMessages = () => {
        setMessagesBlocked(false);
    }

    return (
        <MessageContext.Provider value={{ messages, fetchMessages, sendMessage, clearMessages, messagesBlocked, lockMessages, unlockMessages }}>
            {children}
        </MessageContext.Provider>
    );
};



export default MessageProvider;