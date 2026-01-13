import "./index.scss"

import logo from "../../assets/crimson.svg"
import { useEffect, useState } from "react";

const NotFound = () => {
    const poem = `In the heat of day,
The path dissolves to nothing—
An empty mirage.`

    const [text, setText] = useState("")

    useEffect(() => {
        let i = 1;

        const intervalId = setInterval(() => {
            setText(poem.slice(0, i) + "⬤");
            i++;

            if (i >= poem.length - 1) {
                setText(poem);

                clearInterval(intervalId);
            }
        }, 50);

        return () => clearInterval(intervalId);
    }, [])

    return (
        <div className="notfound">
            <div className="notfound__container">
                <img className="notfound__logo" src={logo} />

                <div className="notfound__title">
                    404 Not Found
                </div>
                <div className="notfound__text">
                    {text}
                    {/* In the heat of day,
                    <br />
                    The path dissolves to nothing—
                    <br />
                    An empty mirage. */}
                </div>
            </div>
        </div>
    );
};

export default NotFound;