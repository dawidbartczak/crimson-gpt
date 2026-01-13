import { createContext } from "react";

import SidebarContextType from "./SidebarContextType";



const SidebarContext = createContext<SidebarContextType | undefined>(undefined);



export default SidebarContext