interface AuthContextType {
    authenticated: boolean;
    register: (email: string, username: string, password: string) => Promise<void>;
    login: (email: string, password: string) => Promise<void>;
    logout: () => void;
    authenticate: () => Promise<void>;
}



export default AuthContextType
