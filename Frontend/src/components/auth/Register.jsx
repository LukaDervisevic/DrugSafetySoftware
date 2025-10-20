import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Register() {
  const [userName, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleRegister = async () => {
    if (userName === "" || email === "" || password === "") {
      return alert("Neispravni parametri registracije");
    }
    const res = await fetch("https://localhost:8443/api/admin/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        korisnickoIme: userName,
        email: email,
        sifra: password,
      }),
    });

    if (!res.ok) {
      const error = await res.json();
      console.log(error);
      return alert(error.message);
    }
    alert("Uspesno prijavljivanje");
    setUsername("");
    setEmail("");
    setPassword("");
    navigate("/login");
  };

  return (
    <div className="bordered bg-[#fefefe] flex flex-col justify-center items-center w-[400px] h-[500px]">
      <div className="flex flex-row justify-start">
        <span className="nunito">Kreirajte nalog</span>
      </div>
      <div className="w-[80%] flex flex-col">
        <span className="nunito">Korisnicko ime</span>
        <input
          type="text"
          className="input-field"
          onChange={(e) => setUsername(e.target.value)}
        />
      </div>
      <div className="w-[80%] flex flex-col mt-[10px]">
        <span className="nunito">Email</span>
        <input
          type="text"
          className="input-field"
          onChange={(e) => setEmail(e.target.value)}
        />
      </div>
      <div className="nunito w-[80%] flex flex-col mt-[10px]">
        <span>Sifra</span>
        <input
          type="password"
          className="input-field"
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>
      <div className="w-[80%] mt-[20px]">
        <button
          className="nunito bordered-btn w-[100%] register-btn"
          onClick={handleRegister}
        >
          Registruj se
        </button>
      </div>
    </div>
  );
}

export default Register;
