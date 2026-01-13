import "./index.scss";

interface TextFieldProps {
    value: string;
    placeholder: string;
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const TextField: React.FC<TextFieldProps> = ({ value, placeholder, onChange }) => {
    return (
        <div className="textfield">
            <input className="textfield__value" type="text" value={value} placeholder={placeholder} onChange={onChange} />
        </div>
    );
};

export default TextField;
