import { useState } from "react";

function SearchBar({ setPisma }) {
  const [lekName, setLekName] = useState("");

  const handleSearchPismo = async () => {
    try {
      // Kreiranje GET zahteva za pisma sa navedenim nazivomLeka
      const res = await fetch(
        `https://localhost:8443/api/pisma?naziv=${encodeURIComponent(lekName)}`,
        {
          method: "GET",
        }
      );
      // Ukoliko status odgovora nije 200 baci gresku
      if (!res.ok) {
        throw new Error("Greška pri pretrazi pisma");
      }
      // Ukoliko je status odgovora 200, postaviti state Pismo na data
      const data = await res.json();
      setPisma(data);
      //Obrada greske
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="bordered bg-[#e6f2f5] w-[95%] h-[125px] flex flex-row justify-center items-center">
      <div className="flex flex-row w-[80%] justify-center">
        <div className="w-[80%] mr-[15px]">
          {/* Na input polju za naziv leka je postavljen event listener onChange, 
          koji ažurira state LekName pri promeni teksta */}
          <input
            type="text"
            placeholder="Unesite lek"
            className="bordered search-bar w-[100%] h-[30px] pl-[10px]"
            onChange={(e) => setLekName(e.target.value)}
          />
        </div>
        <div className="flex-1 ml-[10px]">
          {/* Na dugmetu je postavljen event listener onClick koji poziva handleSearchPismo handler funckiju */}
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
