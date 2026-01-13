import { createContext } from "react";

import ChatContextType from "./ChatContextType";



const ChatContext = createContext<ChatContextType | undefined>(undefined);



export default ChatContext