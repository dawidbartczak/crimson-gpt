import { useEffect, useState } from "react";

import SidebarContext from "./SidebarContext";



const SidebarProvider = ({ children }: { children: React.ReactNode }) => {
    const [opened, setOpened] = useState(() => {
        return localStorage.getItem("opened") === "true";
    });

    useEffect(() => {
        localStorage.setItem("opened", String(opened))
    }, [opened]);

    const toggle = () => {
        setOpened(prev => !prev)
    }

    return (
        <SidebarContext.Provider value={{ opened, toggle }}>
            {children}
        </SidebarContext.Provider>
    );
};



export default SidebarProvider