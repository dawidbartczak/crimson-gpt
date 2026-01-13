import "./index.scss";
import { useState } from "react";

interface PasswordFieldProps {
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder: string;
}

const PasswordField: React.FC<PasswordFieldProps> = ({ value, onChange, placeholder }) => {
    const [hidden, setHidden] = useState(true)

  return (
    <div className="passwordfield">
      <input className="passwordfield__value" type={hidden ? "password" : "text"} value={value} onChange={onChange} placeholder={placeholder} />

      <span className="passwordfield__icon material-symbols-rounded" onClick={() => setHidden(prev => !prev)}>{hidden ? "visibility" : "visibility_off"}</span>
    </div>
  );
};

export default PasswordField;
