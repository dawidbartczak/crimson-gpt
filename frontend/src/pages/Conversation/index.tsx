import "./index.scss"

import { useEffect, useRef, useState } from "react"
import { useNavigate, useParams } from "react-router-dom";

import { useAuth } from "../../contexts/Auth";
import { useChat } from "../../contexts/Chat";
import { useMessage } from "../../contexts/Message";

import Header from "../../components/Header";
import Message from "../../components/Message";
import Answer from "../../components/Answer";
import Prompt from "../../components/Prompt";
import Sidebar from "../../components/Sidebar";



const Conversation = () => {
    const { chatId } = useParams();

    const navigate = useNavigate()

    const { authenticated, authenticate } = useAuth();
    const { chats, fetchChats } = useChat()
    const { messages, fetchMessages, sendMessage, clearMessages, unlockMessages } = useMessage();

    const messagesEndRef = useRef<HTMLDivElement>(null);
    const chatRef = useRef<HTMLDivElement>(null);

    const [chatsFetched, setChatsFetched] = useState(false)

    useEffect(() => {
        unlockMessages();
    }, [chatId])

    useEffect(() => {
        fetchChats().then(() => {
            setChatsFetched(true)
        });
    }, [])

    useEffect(() => {
        authenticate().then(() => {
            if (chatId) {
                if (authenticated) {
                    if (chatsFetched) {
                        const chatOwned = chats.some((chat) => chat.id == chatId);

                        if (!chatOwned) {
                            navigate("/");
                            clearMessages();
                        }
                    }
                } else {
                    navigate("/");
                    clearMessages();
                }
            }
        })
    }, [chatId, chatsFetched, chats, authenticated]);

    // Set title to current chat name
    useEffect(() => {
        const chatTitle = chats.find(chat => chat.id == chatId)?.title || "CrimsonGPT";
        document.title = chatTitle;
    }, [chatId, chats])

    // Fetch chats
    useEffect(() => {
        if (chatId) fetchMessages(chatId)
    }, [chatId]);


    return (
        <div className="conversation">
            {authenticated &&
                <div className="conversation__side">
                    <Sidebar />
                </div>
            }

            <div className="conversation__main">
                <Header />

                <div className="chat" ref={chatRef}>
                    {messages.length > 0 &&
                        <div className="chat__messages">
                            {
                                messages.map((message, index) =>
                                    message.author == 0 ?
                                        <Message text={message.content} key={index} /> :
                                        <Answer text={message.content} animate={message.animate} key={index} onAnimationEnd={() => {
                                            // if (chatRef.current) {
                                            //     chatRef.current.scrollTo({ top: chatRef.current?.scrollHeight, behavior: "smooth" })
                                            // }
                                        }} />
                                )
                            }
                            <div ref={messagesEndRef}></div>
                        </div>
                    }


                    <div className="chat__prompt">
                        <Prompt onSubmit={(value) => {
                            sendMessage(value, chatId).then(() => {
                                setChatsFetched(false);

                                fetchChats().then(() => {
                                    setChatsFetched(true)
                                })
                            });

                            messagesEndRef.current?.scrollIntoView({ behavior: "smooth" })

                            // if (chatRef.current) {
                            //     chatRef.current.scrollTo({ top: chatRef.current?.scrollHeight, behavior: "smooth" })
                            // }
                        }} />
                    </div>
                </div>
            </div>
        </div>
    )
}



export default Conversation;