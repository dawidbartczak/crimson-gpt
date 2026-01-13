import { createContext } from "react";

import AuthContextType from "./AuthContextType";



const AuthContext = createContext<AuthContextType | undefined>(undefined);



export default AuthContext