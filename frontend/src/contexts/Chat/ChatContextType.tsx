import Chat from "../../model/Chat";



interface ChatContextType {
    chats: Chat[];
    createChat: (title: string) => Promise<void>;
    fetchChats: () => Promise<void>;
    updateChat: (chatId: string, title: string) => Promise<void>;
    deleteChat: (chatId: string) => Promise<void>;
}



export default ChatContextType
