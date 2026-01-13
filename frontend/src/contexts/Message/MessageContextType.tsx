import Message from "../../model/Message";



interface MessageContextType {
    messages: Message[];
    messagesBlocked: boolean;
    lockMessages: () => void;
    unlockMessages: () => void;
    fetchMessages: (chatId: string) => Promise<void>;
    sendMessage: (content: string, chatId?: string) => Promise<void>;
    clearMessages: () => Promise<void>;
}



export default MessageContextType
