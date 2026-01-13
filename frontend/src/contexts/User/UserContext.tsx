import { createContext } from "react";

import UserContextType from "./UserContextType";



const UserContext = createContext<UserContextType | undefined>(undefined);



export default UserContext