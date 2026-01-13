import { createContext } from "react";

import MessageContextType from "./MessageContextType";



const MessageContext = createContext<MessageContextType | undefined>(undefined);



export default MessageContext