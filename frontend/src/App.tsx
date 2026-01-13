import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import AuthProvider from './contexts/Auth/AuthProvider';
import ChatProvider from './contexts/Chat/ChatProvider';
import MessageProvider from './contexts/Message/MessageProvider';

import Conversation from './pages/Conversation';
import NotFound from "./pages/NotFound";
import SidebarProvider from './contexts/Sidebar/SidebarProvider';
import Register from './pages/Register';
import Login from './pages/Login';
import UserProvider from './contexts/User/UserProvider';



const App = () => {
    return (
        <Router>
            <AuthProvider>
            <ChatProvider>
            <MessageProvider>
            <SidebarProvider>
            <UserProvider>
                
                <Routes>
                    <Route path="/" element={<Conversation />} />

                    <Route path="/c/:chatId" element={<Conversation />} />

                    <Route path="/register" element={<Register />} />
                    <Route path="/login" element={<Login />} />

                    <Route path="*" element={<NotFound />} />
                </Routes>

            </UserProvider>
            </SidebarProvider>
            </MessageProvider>
            </ChatProvider>
            </AuthProvider>
        </Router>
    )
}



export default App