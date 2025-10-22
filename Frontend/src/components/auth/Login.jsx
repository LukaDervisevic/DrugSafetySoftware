import { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AdminContext } from "../../context/AdminContext";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const { login } = useContext(AdminContext);

  const handleLogin = async () => {
    // Provera formata kredencijala
    if (username === "" || password === "")
      return alert("Molim vas popunite kredencijale");

    try {
      // Kreiranje POST zahteva koji šalje JSON u telu
      const res = await fetch("https://localhost:8443/api/admin/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        // serijalizacija objekta u JSON
        body: JSON.stringify({ korisnickoIme: username, sifra: password }),
      });

      // Ukoliko status odgovora nije 200 baci gresku
      if (!res.ok) {
        const error = await res.json();
        throw new Error(error);
      }
      // Vracanje podataka iz odgovora
      const data = await res.json();
      // Azuriranje AdminContext provider-a sa korisnickim imenom i vracenim tokenom
      login(username, data.token);
      alert("Uspesna prijava");

      // navigacija nazad na pocetnu stranicu
      navigate("/");
    } catch (error) {
      //Obrada neuspelog prijavljivanja
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
          {/* Na input polju za korisnicko ime event listener onChange, koji ažurira state UserName
          pri promeni teksta */}
          <input
            type="text"
            className="input-field"
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div className="flex flex-col w-[70%] mt-[10px]">
          <span>Šifra</span>
          {/* Na input polju za korisnicko ime event listener onChange, koji ažurira state Password
          pri promeni teksta */}
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
          {/* Na dugmetu je postavljen event listener onClick koji poziva handleLogin handler funckiju */}
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
