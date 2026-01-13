import "./index.scss";
import React, { MouseEventHandler, useEffect, useRef, useState } from "react";
import { ButtonChatStyle } from "./ButtonChatStyle";
import ButtonIcon from "../ButtonIcon";
import { ButtonStyle } from "../ButtonStyle";

interface ButtonChatProps {
    text: string;
    style?: ButtonChatStyle;
    onClick: () => void;
    onEdit: (newText: string) => void;
    onDelete: () => void;
}

const ButtonChat: React.FC<ButtonChatProps> = ({ text, style = ButtonChatStyle.INACTIVE, onClick, onEdit, onDelete }) => {
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const [isEditing, setIsEditing] = useState(false);
    const [editedText, setEditedText] = useState(text);
    const [isHovered, setIsHovered] = useState(false);
    const buttonChatRef = useRef<HTMLButtonElement | null>(null);
    const menuRef = useRef<HTMLDivElement | null>(null);

    // Toggle menu visibility
    const handleIconClick: MouseEventHandler = (event) => {
        event.stopPropagation(); // Prevent event propagation to avoid closing the menu when clicking the icon
        setIsMenuOpen((prev) => !prev);
    };

    // Edit button click handler
    const editAction: MouseEventHandler = (event) => {
        event.stopPropagation(); // Prevent the click from closing the menu
        setIsEditing(true);
        setIsMenuOpen(false); // Close the menu when editing starts
    };

    // Delete button click handler
    const deleteAction: MouseEventHandler = (event) => {
        event.stopPropagation(); // Prevent the click from closing the menu
        onDelete(); // Call the delete handler
        setIsMenuOpen(false); // Close the menu after deleting
    };

    // Handle click outside the button or menu
    const handleClickOutside = (event: MouseEvent) => {
        if (
            buttonChatRef.current && !buttonChatRef.current.contains(event.target as Node) && 
            menuRef.current && !menuRef.current.contains(event.target as Node)
        ) {
            setIsMenuOpen(false); // Close the menu when clicking outside
        }
    };

    // Handle text change while editing
    const handleTextChange: React.ChangeEventHandler<HTMLInputElement> = (event) => {
        setEditedText(event.target.value);
    };

    // Handle blur event (focus loss) for the input field
    const handleBlur = () => {
        onEdit(editedText); // Submit the edited text
        setIsEditing(false); // Stop editing
    };

    // Handle Enter key press (submit text when editing)
    const handleKeyPress: React.KeyboardEventHandler<HTMLInputElement> = (event) => {
        console.log("")
        
        if (event.key === "Enter") {
            onEdit(editedText); // Submit the new text
            setIsEditing(false); // Stop editing
        }
    };

    // Attach the event listener to handle outside clicks
    useEffect(() => {
        if (isMenuOpen) {
            document.addEventListener("mousedown", handleClickOutside);
        } else {
            document.removeEventListener("mousedown", handleClickOutside);
        }

        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [isMenuOpen]);

    return (
        <button 
            className={`buttonchat buttonchat--${style} ${isMenuOpen ? "buttonchat--hover" : ""}`} 
            onClick={onClick} 
            ref={buttonChatRef}
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
        >
            <div className="buttonchat__text">
                {isEditing ? (
                    <input
                        className="buttonchat__input"
                        type="text"
                        value={editedText}
                        onChange={handleTextChange}
                        onBlur={handleBlur}  // Submit on blur
                        onKeyPress={handleKeyPress}  // Submit on Enter
                        autoFocus
                    />
                ) : (
                    editedText
                )}
            </div>
            <div className="buttonchat__buttons">
                {(isHovered || isMenuOpen) && (
                    <ButtonIcon icon="more_horiz" style={style === ButtonChatStyle.ACTIVE ? ButtonStyle.TRANSPARENT_BRIGHT : ButtonStyle.TRANSPARENT_DARK} onClick={handleIconClick} />
                )}
                {isMenuOpen && (
                    <div className="buttonchat__menu" ref={menuRef}>
                        <div className="buttonchat__button buttonchat__button--edit" onClick={editAction}>
                            <span className="material-symbols-rounded">stylus</span>
                            Edit
                        </div>
                        <div className="buttonchat__button buttonchat__button--delete" onClick={deleteAction}>
                            <span className="material-symbols-rounded">delete</span>
                            Delete
                        </div>
                    </div>
                )}
            </div>
        </button>
    );
};

export default ButtonChat;
