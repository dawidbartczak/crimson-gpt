import "./index.scss";

import { useState, useRef, useEffect } from "react";

import ButtonIcon from "../Button/ButtonIcon";
import { useMessage } from "../../contexts/Message";
import { ButtonStyle } from "../Button/ButtonStyle";

interface PromptProps {
    onSubmit: (value: string) => void;
}

const Prompt: React.FC<PromptProps> = ({ onSubmit }) => {
    const [value, setValue] = useState("");
    const inputRef = useRef<HTMLTextAreaElement>(null);

    const { messagesBlocked } = useMessage();

    // Function to handle textarea height expansion
    const adjustTextareaHeight = () => {
        if (inputRef.current) {
            inputRef.current.style.height = "auto"; // Reset height
            inputRef.current.style.height = `${Math.min(inputRef.current.scrollHeight, 200)}px`; // Expand up to max height
            inputRef.current.style.overflowY = inputRef.current.scrollHeight > 200 ? "scroll" : "hidden"; // Enable scroll if needed
        }
    };

    useEffect(() => {
        adjustTextareaHeight();
    }, [value]);

    const handleKeyDown = (event: React.KeyboardEvent) => {
        if (event.key === "Enter" && !event.shiftKey && value.length > 0) {
            event.preventDefault();
            onSubmit(value);
            setValue(""); // Clear input after submitting
        }
    };

    const handleInputChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        setValue(event.target.value);
    };

    return (
        <div className="prompt">
            <textarea
                ref={inputRef}
                className="prompt__input"
                value={value}
                placeholder="Send message"
                onChange={handleInputChange}
                onKeyDown={handleKeyDown}
                rows={1}
                style={{ maxHeight: "200px", overflowY: "hidden" }} // Initial state
            />

            <ButtonIcon
                icon="arrow_upward"
                style={messagesBlocked ? ButtonStyle.DISABLED : ButtonStyle.PRIMARY}
                onClick={() => {
                    if (value.length > 0) {
                        onSubmit(value);
                        setValue(""); // Clear input after submitting
                    }
                }}
            />
        </div>
    );
};

export default Prompt;
