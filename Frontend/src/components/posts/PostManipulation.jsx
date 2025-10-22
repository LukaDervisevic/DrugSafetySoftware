import { useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import DrugTable from "../DrugTable";

function PostManipulation({ operation }) {
  const { id } = useParams();
  const location = useLocation();
  const [pismo, setPismo] = useState(location.state?.pismo ?? null);
  const [lekovi, setLekovi] = useState([]);

  const [nazivLeka, setNazivLeka] = useState("");
  const [title, setTitle] = useState("");
  const [text, setText] = useState("");
  const [selectedLekovi, setSelectedLekovi] = useState([]);
  const [loading, setLoading] = useState(true);
  const [pdf, setPdf] = useState(null);

  // UseEffect azurira state-ove Title, Text i SelectedLekovi,
  // nakon promene state-a Pismo
  useEffect(() => {
    if (pismo) {
      setTitle(pismo.naslovPisma || "");
      setText(pismo.tekstPisma || "");
      setSelectedLekovi(pismo.lekovi ? pismo.lekovi : []);
    }
  }, [pismo]);

  // UseEffect koji ukoliko je operacija azuriranje salje GET zahtev
  // za pismo sa datim idjem
  useEffect(() => {
    if (!pismo && operation === "update") {
      fetch(`https://localhost:8443/api/pisma/${id}`)
        .then((res) => res.json())
        .then((data) => setPismo(data))
        .catch((err) => console.error("Greska pri ucitavanju pisma:", err));
    }
  }, [operation, pismo, id]);

  function isSameId(lek1, lek2) {
    return (
      lek1.sifraProizvoda === lek2.sifraProizvoda &&
      lek1.sifraProizvodjaca === lek2.sifraProizvodjaca &&
      lek1.sifraNosiocaDozvole === lek2.sifraNosiocaDozvole &&
      lek1.vrstaResenja === lek2.vrstaResenja &&
      lek1.atc === lek2.atc &&
      lek1.ean === lek2.ean &&
      lek1.jkl === lek2.jkl
    );
  }

  const handleLekSearch = async () => {
    // Kreiranje GET zahteva sa parametrom naziv
    const res = await fetch(
      `https://localhost:8443/api/lekovi/search?naziv=${encodeURIComponent(
        nazivLeka
      )}`,
      {
        method: "GET",
      }
    );
    // Ukoliko status odgovora nije 200 prikazi gresku
    if (!res.ok) {
      const error = await res.json();
      alert(error.message);
    }
    // Ukoliko je status odgovora 200 vrati lekove
    const data = await res.json();
    setLekovi(data);
  };

  // Handler funckija koja postavlja state Selected Lekovi
  // na promenjeni niz lekova koji se dobija filtriranjem
  const handleLekRemoval = (lekToRemove) => {
    setSelectedLekovi(
      selectedLekovi.filter((lek) => !isSameId(lekToRemove, lek))
    );
  };

  const handlePismoCreation = async () => {
    // Kreiranje objekta pismo na osnovu state-ova
    //  Title, Text, SelectedLekovi
    if (title === "" || text === "" || lekovi.length === 0) {
      return;
    }

    const pismo = {
      naslovPisma: title,
      tekstPisma: text,
      lekovi: selectedLekovi,
      datum: new Date().toLocaleDateString("en-CA"),
    };
    // Kreiranje form data objekta za slanje
    const formData = new FormData();
    // Kreiranje Blob-a na osnovu serijalizovanog objekta pismo
    const jsonBlob = new Blob([JSON.stringify(pismo)], {
      type: "application/json",
    });
    // Dodavanje Blob-a form data objektu
    formData.append("pismo", jsonBlob);
    if (pdf) {
      // Dodavanje pdf-a form data objektu
      formData.append("pdf", pdf);
    }
    // Kreiranje POST zahteva za kreiranje pisma sa JWT tokenom za autorizaciju
    // Post zahtev je tipa form-data
    const res = await fetch("https://localhost:8443/api/pisma", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
      },
      body: formData,
    });
    // Ukoliko status odgovora nije 200 prikazi gresku
    if (!res.ok) {
      const error = await res.json();
      alert(error.message);
      return;
    }
    // U suprotnom prikazi poruku Pismo je kreirano i podesi state-ove na prazne vrednosti
    await res.json();
    alert("Pismo je kreirano");
    setSelectedLekovi([]);
    setPdf(null);
    setText("");
    setTitle("");
  };

  const handlePismoUpdate = async () => {
    // Kreiranje objekta na onsovu state-ova
    const pismoToUpdate = {
      id: pismo.id,
      naslovPisma: title,
      tekstPisma: text,
      lekovi: selectedLekovi,
      datum: pismo.datum,
    };
    // kreiranje form data objekta
    const formData = new FormData();
    // Kreiranje Blob-a na osnovu serijalizovanog pisma
    const jsonBlob = new Blob([JSON.stringify(pismoToUpdate)], {
      type: "application/json",
    });
    // Dodavanje Blob-a u form data
    formData.append("pismo", jsonBlob);
    // Dodavanje pdf dokumenta u form data
    if (pdf) {
      formData.append("pdf", pdf);
    }
    // Kreiranje PUT zahteva tipa form data sa JWT tokenom u zaglavlju
    const res = await fetch(
      `https://localhost:8443/api/pisma/${pismoToUpdate.id}`,
      {
        method: "PUT",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
        body: formData,
      }
    );
    // Ukoliko status odgovora nije 200 prikazi gresku
    if (!res.ok) {
      const error = await res.json();
      alert(error.message);
      return;
    }
    // Ukoliko je status odgovora 200 onda postavi state Pismo
    // i prikazi uspesnu poruku
    const data = await res.json();
    setPismo(data);
    alert("Pismo je uspesno azurirano");
  };

  const handlePdfUpload = (event) => {
    // Preuzimanje pdf-a iz input field-a
    const pdf = event.target.files[0];
    // Postavljanje state-a Pdf na pdf
    setPdf(pdf);
  };

  useEffect(() => {
    // Pri iniciajlnom renderovanju komponente za kreiranje pisma,
    // useEffect kreira GET zahtev za vracanje lekova
    const fetchLekovi = async () => {
      try {
        const res = await fetch("https://localhost:8443/api/lekovi", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        });
        if (!res.ok) throw new Error("Greska pri ucitavanju lekova");
        const data = await res.json();
        // postavljanje state-a Lekovi na data
        setLekovi(data);
      } catch (error) {
        alert("Greska pri vracanju lekova");
      } finally {
        // postavljanje state-a Loading na false, zavrseno ucitavanje
        setLoading(false);
      }
    };
    fetchLekovi();
  }, []);

  if (loading) return <div className="nunito">Ucitavanje...</div>;

  return (
    <div>
      <div className="bordered bg-[#f9fafc] w-[1700px] h-[600px] flex flex-row create-post-font">
        <div className="flex flex-col">
          <form
            action=""
            method="post"
            className="flex flex-col ml-[30px] mt-[30px]"
          >
            <span className="mb-[5px]">Naslov pisma</span>
            {/* Na input polju za naziv pisma postavljen je event listener onChange,
         koji ažurira state Title pri promeni teksta */}
            <input
              type="text"
              className="input-field mb-[20px]"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
            <span className="mb-[5px]">Sadrzaj pisma</span>
            {/* Na textarea polju za tekst pisma postavljen je event listener onChange,
         koji ažurira state Text pri promeni teksta */}
            <textarea
              className="input-field mb-[20px]"
              rows={10}
              cols={50}
              style={{ resize: "none" }}
              value={text}
              onChange={(e) => setText(e.target.value)}
            ></textarea>
            <div className="flex flex-row items-center">
              <span className="mr-[20px]">Dokument</span>
              {/* Na textarea polju za pdf dokument postavljen je event listener onChange,
         koji ažurira state Pdf pri promeni dokumenta */}
              <input
                type="file"
                accept="application/pdf"
                onChange={handlePdfUpload}
              />
            </div>
          </form>
          <div className="flex flex-row items-center justify-start ml-[30px] mt-[10px]">
            {/* Na dugmetu je postavljen event listener onClick koji poziva handlePismoCreation 
            ukoliko je u pitanju kreiranje ili handlePismoUpdate ako je u pitanju azuriranje*/}
            <button
              className="bordered-btn bg-[#009fac] text-[#f8fbfc] nunito create-post-btn"
              onClick={
                operation === "create" ? handlePismoCreation : handlePismoUpdate
              }
            >
              Objavi
            </button>
          </div>
          <div className="flex flex-row mt-[10px] ml-[30px] flex-wrap">
            <span>Izabrani lekovi: </span>
            {selectedLekovi.length !== 0 ? (
              selectedLekovi.map((lek, i) => (
                <div
                  className="bordered drug-label"
                  key={i}
                  onClick={() => handleLekRemoval(lek)}
                >
                  {lek.nazivLeka}
                </div>
              ))
            ) : (
              <div />
            )}
          </div>
        </div>
        <div className="ml-[30px] flex-1 mt-[65px] flex flex-col">
          <div className=" mb-[20px]">
            <div className="flex flex-row w-[40%]">
              {/* Na input polju za naziv Leka je event listener onChange,
              koji ažurira state NazivLeka pri promeni teksta */}
              <input
                type="text"
                placeholder="naziv leka"
                className="bordered table-search-input flex-1 mr-[10px]"
                onChange={(e) => setNazivLeka(e.target.value)}
              />
              {/* Na dugmetu je postavljen event listener onClick koji poziva handleLekSearch handler funckiju */}
              <button
                className="bordered-btn nunito search-btn bg-[#009fac] text-[#f8fbfc]"
                onClick={handleLekSearch}
              >
                Pretraga
              </button>
            </div>
          </div>
          <DrugTable
            lekovi={lekovi}
            onRowClick={(lek) =>
              setSelectedLekovi((lekovi) => [...lekovi, lek])
            }
          ></DrugTable>
        </div>
      </div>
    </div>
  );
}

export default PostManipulation;
