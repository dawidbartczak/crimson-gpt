import { useMessage } from "../../contexts/Message";
import "./index.scss"
import { useEffect, useState } from "react"

interface AnswerProps {
    text: string;
    animate?: boolean;
    onAnimationEnd?: () => void
}

const Answer: React.FC<AnswerProps> = ({ text, animate = false, onAnimationEnd }) => {
    const [content, setContent] = useState("")

    const { unlockMessages } = useMessage()

    useEffect(() => {
        if(!animate) return;

        let i = 1;

        const intervalId = setInterval(() => {
            // setContent(text.slice(0, i) + "✦");
            setContent(text.slice(0, i) + "⬤");
            i++;

            if (i >= text.length - 1) {
                setContent(text);

                clearInterval(intervalId);

                unlockMessages()

                if (onAnimationEnd) onAnimationEnd()
            }
        }, 3);

        return () => clearInterval(intervalId);
    }, [])

    return (
        <div className="answer">{ animate ? content : text }</div>
    )
}

export default Answer;