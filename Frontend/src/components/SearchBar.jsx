import { useState } from "react";

function SearchBar({ setPisma }) {
  const [lekName, setLekName] = useState("");

  const handleSearchPismo = async () => {
    try {
      const res = await fetch(
        `https://localhost:8443/api/pisma?naziv=${encodeURIComponent(lekName)}`,
        {
          method: "GET",
        }
      );

      if (!res.ok) {
        throw new Error("Greška pri pretrazi pisma");
      }
      const data = await res.json();
      setPisma(data);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="bordered bg-[#e6f2f5] w-[95%] h-[125px] flex flex-row justify-center items-center">
      <div className="flex flex-row w-[80%] justify-center">
        <div className="w-[80%] mr-[15px]">
          <input
            type="text"
            placeholder="Unesite lek"
            className="bordered search-bar w-[100%] h-[30px] pl-[10px]"
            onChange={(e) => setLekName(e.target.value)}
          />
        </div>
        <div className="flex-1 ml-[10px]">
          <button
            className="bordered-btn nunito search-btn bg-[#009fac] text-[#f8fbfc]"
            onClick={handleSearchPismo}
          >
            Pretraga
          </button>
        </div>
      </div>
    </div>
  );
}

export default SearchBar;
