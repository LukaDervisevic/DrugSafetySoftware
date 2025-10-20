import NavBar from "./nav/NavBar";
import SearchBar from "./SearchBar";
import Posts from "./posts/Posts";
import Info from "./Info";

import { useState, useEffect } from "react";

function Main() {
  const [pisma, setPisma] = useState([]);

  useEffect(() => {
    fetch("https://localhost:8443/api/pisma")
      .then((res) => res.json())
      .then((data) => {
        setPisma(data);
      })
      .catch((err) => console.error("Greska pri vracanju pisama: ", err));
  }, []);

  return (
    <div className=" w-[80%] h-[90%] flex flex-row ">
      <div className="bordered w-[75%] h-[100%] bg-[#f9fafc] rounded-2xl flex flex-col items-center">
        <div className="bg-[#f9fafc] w-[100%] h-[80px]">
          <NavBar />
        </div>
        <SearchBar setPisma={setPisma} />
        <Posts pisma={pisma} setPisma={setPisma} />
      </div>
      <Info />
    </div>
  );
}

export default Main;
