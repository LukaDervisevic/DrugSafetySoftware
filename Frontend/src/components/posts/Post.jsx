import { useContext } from "react";
import { AdminContext } from "../../context/AdminContext";
import { useNavigate } from "react-router-dom";

function Post({ pismo, setPisma }) {
  const { isLoggedIn } = useContext(AdminContext);
  const navigate = useNavigate();

  const handlePdfLoad = async () => {
    const pdf = `https://localhost:8443/api/pisma/${pismo.id}/pdf`;
    window.open(pdf, "_blank");
  };

  const handleDeletePismo = async (id) => {
    const confirmWindow = window.confirm(
      "Da li sigurno zelite da obrisete pismo?"
    );
    if (!confirmWindow) return;
    try {
      const res = await fetch(`https://localhost:8443/api/pisma/${id}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });
      if (!res.ok) throw new Error("Greska pri brisanju pisma");
      setPisma((pisma) => pisma.filter((pismo) => pismo.id !== id));
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="bordered bg-[#fefefe] w-[80%] min-h-[150px] mt-[20px] flex flex-row flex-shrink-0">
      <div className="nunito w-[75%] flex flex-col ">
        <span className="ml-[20px] mt-[20px]">{pismo.naslovPisma}</span>
        <span className="ml-[20px] mt-[10px] pb-[20px] pr-[10px]">
          {pismo.tekstPisma}
        </span>
      </div>
      <hr className="border-t border-gray-300 my-4 opacity-40" />
      <div className="flex-1 flex flex-col  justify-center items-center nunito btn-container">
        <div className="bordered bg-[#e6f2f5] p-[10px] mt-[10px] mb-[5px]">
          <span>{pismo.datum}</span>
        </div>
        <button
          className="bordered-btn post-btn nunito bg-[#009fac] text-[#f8fbfc]"
          onClick={handlePdfLoad}
        >
          Pismo
        </button>
        {isLoggedIn ? (
          <div className="flex flex-col">
            <button
              className="bordered-btn alter-btn nunito mb-[10px]"
              onClick={() => handleDeletePismo(pismo.id)}
            >
              Obrisi
            </button>
            <button
              className="bordered-btn alter-btn nunito mb-[10px]"
              onClick={() => {
                console.log(pismo);
                navigate(`/posts/${pismo.id}/edit`, { state: { pismo } });
              }}
            >
              Promeni
            </button>
          </div>
        ) : (
          <div />
        )}
      </div>
    </div>
  );
}

export default Post;
