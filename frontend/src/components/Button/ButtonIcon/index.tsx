import "./index.scss"
import React, { MouseEventHandler } from "react"
import { ButtonStyle } from "../ButtonStyle";

interface ButtonIconProps {
    icon: string;
    style?: ButtonStyle;
    onClick?: MouseEventHandler<HTMLDivElement>; 
}

const ButtonIcon: React.FC<ButtonIconProps> = ({ icon, style = ButtonStyle.PRIMARY, onClick }) => {
    return (
        <div className={`buttonicon buttonicon--${style}`} onClick={onClick}>
            <span className="material-symbols-rounded">{icon}</span>
        </div>
    )
}

export default ButtonIcon;