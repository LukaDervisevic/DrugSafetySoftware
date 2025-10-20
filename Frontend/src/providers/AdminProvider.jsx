import { useState, useEffect } from "react";
import { AdminContext } from "../context/AdminContext";

export function AdminProvider({ children }) {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [korisnickoIme, setKorisnickoIme] = useState("");

  useEffect(() => {
    const jwtToken = localStorage.getItem("jwtToken");
    const ime = localStorage.getItem("korisnickoIme");
    if (jwtToken) setIsLoggedIn(true);
    if (ime) setKorisnickoIme(ime);
  }, []);

  const login = (ime, token) => {
    setIsLoggedIn(true);
    setKorisnickoIme(ime);
    localStorage.setItem("jwtToken", token);
    console.log(localStorage.getItem("jwtToken"));
    localStorage.setItem("korisnickoIme", ime);
  };
  const logout = () => {
    setIsLoggedIn(false);
    setKorisnickoIme("");
    localStorage.removeItem("jwtToken");
    localStorage.removeItem("korisnickoIme");
  };

  return (
    <AdminContext.Provider value={{ isLoggedIn, korisnickoIme, login, logout }}>
      {children}
    </AdminContext.Provider>
  );
}
