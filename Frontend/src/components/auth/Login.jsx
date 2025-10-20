import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AdminContext } from "../../context/AdminContext";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const { login } = useContext(AdminContext);

  const handleLogin = async () => {
    if (username === "" || password === "")
      return alert("Molim vas popunite kredencijale");

    try {
      const res = await fetch("https://localhost:8443/api/admin/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ korisnickoIme: username, sifra: password }),
      });

      if (!res.ok) {
        const error = await res.json();
        return alert(error.message);
      }

      const data = await res.json();
      login(username, data.token);

      navigate("/");
    } catch (error) {
      console.log("Login error: ", error);
      alert("Neuspesno prijavljivanje");
    }
  };

  const handleRegister = () => {
    navigate("/register");
  };

  return (
    <div className="bordered bg-[#fefefe] flex flex-col justify-center items-center w-[400px] h-[450px] nunito login-container">
      <div className=" w-[100%] flex flex-col items-center ml-[50px]">
        <div className="w-[100%] flex flex-row justify-start ">
          <span className="login-header">Dobrodošli nazad</span>
        </div>
        <div className="w-[100%] flex flex-row justify-start mt-[5px]">
          <span>Unesite svoje kredencijale za pristup nalogu</span>
        </div>
      </div>
      <img
        src="/images/alims-nobg.png"
        alt="alims"
        className="w-[150px] h-[150px] mt-[-20px]"
      />
      <div className=" w-[100%] flex flex-col items-center mt-[-40px]">
        <div className="flex flex-col w-[70%]">
          <span>Korisničko ime</span>
          <input
            type="text"
            className="input-field"
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div className="flex flex-col w-[70%] mt-[10px]">
          <span>Šifra</span>
          <input
            type="password"
            className="input-field"
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <div className="w-[70%] flex flex-row justify-end">
          <button
            className="bg-transparent no-account-btn"
            onClick={handleRegister}
          >
            <span>Nemate nalog?</span>
          </button>
        </div>
        <div className="w-[70%] mt-[20px]">
          <button
            className="bordered-btn login-btn w-[100%]"
            onClick={handleLogin}
          >
            Login
          </button>
        </div>
      </div>
    </div>
  );
}

export default Login;
