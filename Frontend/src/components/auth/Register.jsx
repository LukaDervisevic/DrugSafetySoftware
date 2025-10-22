import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Register() {
  const [userName, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleRegister = async () => {
    // Provera ispravnih parametara registracije
    if (userName === "" || email === "" || password === "") {
      return alert("Neispravni parametri registracije");
    }

    // Kreiranje POST zahteva sa JSON telom
    const res = await fetch("https://localhost:8443/api/admin/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      // Serijalizacija objekta u JSON
      body: JSON.stringify({
        korisnickoIme: userName,
        email: email,
        sifra: password,
      }),
    });
    // Ukoliko status odgovora nije 200 prikazi poruku greske
    if (!res.ok) {
      const error = await res.json();
      alert(error.message);
    }
    // Ako je status odgovora 200, prikazi poruku uspesne registracije,
    // azuriraj state Username,Email,Password na prazne stringove i
    // navigiraj nazad na stranicu za prijavljivanje
    alert("Uspesna registracija");
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
        {/* Na input polju za korisnicko ime postavljen je event listener onChange,
         koji ažurira state UserName pri promeni teksta */}
        <input
          type="text"
          className="input-field"
          onChange={(e) => setUsername(e.target.value)}
        />
      </div>
      <div className="w-[80%] flex flex-col mt-[10px]">
        <span className="nunito">Email</span>
        {/* Na input polju za email postavljen je event listener onChange,
         koji ažurira state Email pri promeni teksta */}
        <input
          type="text"
          className="input-field"
          onChange={(e) => setEmail(e.target.value)}
        />
      </div>
      <div className="nunito w-[80%] flex flex-col mt-[10px]">
        <span>Sifra</span>
        {/* Na input polju za sifru postavljen je event listener onChange,
         koji ažurira state Sifra pri promeni teksta */}
        <input
          type="password"
          className="input-field"
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>
      <div className="w-[80%] mt-[20px]">
        {/* Na dugmetu je postavljen event listener onClick koji poziva handleRegister handler funckiju */}
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
