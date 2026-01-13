import "./index.scss";

import { FormEvent, useState } from "react";
import { Link } from "react-router-dom";

import { useAuth } from "../../contexts/Auth";

import TextField from "../../components/TextField";
import ButtonText from "../../components/Button/ButtonText";
import PasswordField from "../../components/PasswordField";



const Register = () => {
    const { register } = useAuth();

    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const onSubmit = (event: FormEvent) => {
        event.preventDefault();

        register(email, username, password);
    }

    return (
        <div className="register">
            <div className="register__left"></div>
            <div className="register__right">
                <div className="register__form">
                    <div className="register__title">Register</div>

                    <form className="register__fields" onSubmit={onSubmit}>
                        <TextField
                            value={email}
                            placeholder="E-mail"
                            onChange={(event) => setEmail(event.target.value)}
                        />
                        <TextField
                            value={username}
                            placeholder="Username"
                            onChange={(event) => setUsername(event.target.value)}
                        />
                        <PasswordField
                            value={password}
                            placeholder="Password"
                            onChange={(event) => setPassword(event.target.value)}
                        />
                        <ButtonText
                            text="Sign up"
                        />
                    </form>

                    <div className="register__info">
                        Already have an account? <Link className="register__link" to="/login">Log in here</Link>
                    </div>
                </div>
            </div>
        </div>
    );
};



export default Register;