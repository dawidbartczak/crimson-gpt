import "./index.scss"
import React from "react"
import { ButtonStyle } from "../ButtonStyle";

interface ButtonTextProps {
    text: string;
    style?: ButtonStyle;
    onClick?: () => void; 
}

const ButtonText: React.FC<ButtonTextProps> = ({ text, style = ButtonStyle.PRIMARY, onClick }) => {
    return (
        <button className={`buttontext buttontext--${style}`} onClick={onClick}>{text}</button>
    )
}

export default ButtonText;