import "./index.scss"

import { useNavigate, useParams } from "react-router-dom"

import { useChat } from "../../contexts/Chat"
import { useSidebar } from "../../contexts/Sidebar"

import { ButtonStyle } from "../Button/ButtonStyle"
import { ButtonChatStyle } from "../Button/ButtonChat/ButtonChatStyle"
import ButtonIcon from "../Button/ButtonIcon"
import ButtonChat from "../Button/ButtonChat"



const Sidebar = () => {
    const { chatId } = useParams()
    const navigate = useNavigate();

    const { chats, createChat, deleteChat, updateChat } = useChat();
    const { opened } = useSidebar();

    return (
        <nav className={`sidebar ${opened ? "" : "sidebar--hidden"}`}>
            <div className="sidebar__header">
                <div className="sidebar__title">Chats</div>
                <ButtonIcon icon="edit_square" style={ButtonStyle.TRANSPARENT} onClick={() => {createChat("New chat")}} />
            </div>
            <div className="sidebar__chats">
                {
                    chats.map((chat) => {
                        return (
                            <ButtonChat
                                text={chat.title}
                                style={chat.id == (chatId ?? -1) ? ButtonChatStyle.ACTIVE : ButtonChatStyle.INACTIVE}
                                onClick={() => navigate(`/c/${chat.id}`)}
                                onEdit={(newText) => {updateChat(chat.id, newText)}}
                                onDelete={() => {deleteChat(chat.id)}}
                                key={chat.id} />
                        )
                    })
                }
            </div>
        </nav>
    )
}

export default Sidebar