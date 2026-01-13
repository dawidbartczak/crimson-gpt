import "./index.scss"

import { useNavigate } from "react-router-dom"

import { useAuth } from "../../contexts/Auth/index.tsx"
import { useMessage } from "../../contexts/Message/index.tsx"
import { useSidebar } from "../../contexts/Sidebar"

import { ButtonStyle } from "../Button/ButtonStyle.tsx"
import ButtonText from "../Button/ButtonText/index.tsx"
import ButtonIcon from "../Button/ButtonIcon/index.tsx"

import logo from "../../assets/crimson.svg";
import { useUser } from "../../contexts/User/index.tsx"



const Header = () => {
    const navigate = useNavigate();

    const { username } = useUser()

    const { authenticated, logout } = useAuth();
    const { clearMessages } = useMessage();
    const { toggle } = useSidebar();

    return (
        <header className="header">
            <div className="header__left">
                {authenticated ?
                    <ButtonIcon
                        icon="sort"
                        style={ButtonStyle.TRANSPARENT}
                        onClick={toggle}
                    />
                    :
                    <ButtonIcon
                        icon="edit_square"
                        style={ButtonStyle.TRANSPARENT}
                        onClick={clearMessages}
                    />
                }

                <img className="header__logo" src={logo} />

                <div className="header__title">CrimsonGPT</div>
            </div>

            <div className="header__right">
                {authenticated ? (
                    <>
                        <div className="header__username">
                            {username}
                        </div>

                        <ButtonIcon
                            icon="logout"
                            style={ButtonStyle.TRANSPARENT}
                            onClick={logout}
                        />
                    </>

                ) : (
                    <>
                        <ButtonText
                            text="Log in"
                            onClick={() => navigate("/login")}
                        />
                        <ButtonText
                            text="Register"
                            style={ButtonStyle.SECONDARY}
                            onClick={() => navigate("/register")}
                        />
                    </>
                )
                }

            </div>
        </header>
    )
}



export default Header