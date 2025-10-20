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

  useEffect(() => {
    if (pismo) {
      setTitle(pismo.naslovPisma || "");
      setText(pismo.tekstPisma || "");
      setSelectedLekovi(pismo.lekovi ? pismo.lekovi : []);
    }
  }, [pismo]);

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
    try {
      const res = await fetch(
        `https://localhost:8443/api/lekovi/search?naziv=${encodeURIComponent(
          nazivLeka
        )}`,
        {
          method: "GET",
        }
      );
      if (!res.ok) {
        throw new Error("Greska pri vracanju lekova po nazivu");
      }
      const data = await res.json();
      setLekovi(data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleLekRemoval = (lekToRemove) => {
    setSelectedLekovi(
      selectedLekovi.filter((lek) => !isSameId(lekToRemove, lek))
    );
  };

  const handlePismoCreation = async () => {
    const pismo = {
      naslovPisma: title,
      tekstPisma: text,
      lekovi: selectedLekovi,
      datum: new Date().toLocaleDateString("en-CA"),
    };
    console.log(pismo);

    try {
      const formData = new FormData();
      const jsonBlob = new Blob([JSON.stringify(pismo)], {
        type: "application/json",
      });
      formData.append("pismo", jsonBlob);
      if (pdf) {
        formData.append("pdf", pdf);
      }

      const res = await fetch("https://localhost:8443/api/pisma", {
        method: "POST",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
        body: formData,
      });
      if (!res.ok) {
        const err = await res.json();
        console.error(err);
        return;
      }
      await res.json();
      alert("Pismo je kreirano");
      setSelectedLekovi([]);
      setPdf(null);
      setText("");
      setTitle("");
    } catch (error) {
      console.error(error);
    }
  };

  const handlePismoUpdate = async () => {
    const pismoToUpdate = {
      id: pismo.id,
      naslovPisma: title,
      tekstPisma: text,
      lekovi: selectedLekovi,
      datum: pismo.datum,
    };

    try {
      const formData = new FormData();
      const jsonBlob = new Blob([JSON.stringify(pismoToUpdate)], {
        type: "application/json",
      });
      formData.append("pismo", jsonBlob);
      if (pdf) {
        formData.append("pdf", pdf);
      }

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
      if (!res.ok) {
        throw new Error("Greska pri azuriranju pisma");
      }
      const data = await res.json();
      console.log(data);
      setPismo(data);
      alert("Pismo je uspesno azurirano");
    } catch (error) {
      console.error(error);
    }
  };

  const handlePdfUpload = (event) => {
    const pdf = event.target.files[0];
    setPdf(pdf);
  };

  useEffect(() => {
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
        setLekovi(data);
      } catch (error) {
        console.error("Greska pri vracanju lekova: ", error);
      } finally {
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
            <input
              type="text"
              className="input-field mb-[20px]"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
            />
            <span className="mb-[5px]">Sadrzaj pisma</span>
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
              <input
                type="file"
                accept="application/pdf"
                onChange={handlePdfUpload}
              />
            </div>
          </form>
          <div className="flex flex-row items-center justify-start ml-[30px] mt-[10px]">
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
              <input
                type="text"
                placeholder="naziv leka"
                className="bordered table-search-input flex-1 mr-[10px]"
                onChange={(e) => setNazivLeka(e.target.value)}
              />
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
