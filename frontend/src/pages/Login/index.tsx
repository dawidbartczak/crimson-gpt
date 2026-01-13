import "./index.scss";

import { FormEvent, useState } from "react";
import { Link } from "react-router-dom";

import { useAuth } from "../../contexts/Auth";

import TextField from "../../components/TextField";
import ButtonText from "../../components/Button/ButtonText";
import PasswordField from "../../components/PasswordField";

const Login = () => {
    const { login } = useAuth();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const onSubmit = (event: FormEvent) => {
        event.preventDefault();

        login(email, password);
    }

    return (
        <div className="login">
            <div className="login__left"></div>

            <div className="login__right">
                <div className="login__form">
                    <div className="login__title">Log in</div>

                    <form className="login__fields" onSubmit={onSubmit}>
                        <TextField
                            value={email}
                            placeholder="E-mail"
                            onChange={(event) => setEmail(event.target.value)}
                        />
                        
                        <PasswordField
                            value={password}
                            placeholder="Password"
                            onChange={(event) => setPassword(event.target.value)}
                        />
                        
                        <ButtonText
                            text="Log in"
                        />
                    </form>

                    <div className="login__info">
                        No account yet? <Link className="login__link" to="/register">Register here.</Link>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Login;
