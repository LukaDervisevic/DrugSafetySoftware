import { useContext } from "react";
import { AdminContext } from "../../context/AdminContext";
import { useNavigate } from "react-router-dom";

function AdminNav() {
  const { logout } = useContext(AdminContext);
  const navigate = useNavigate();

  const handleCreatePismo = () => {
    navigate("/posts/new");
  };

  const handleLogout = () => {
    const confirmWindow = window.confirm("Da li sigurno zelite da se odjavite");
    if (!confirmWindow) return;
    logout();
  };

  return (
    <div className="mr-[50px] flex flex-row items-center ">
      <button
        className="bordered-btn create-btn bg-[#009fac] text-[#f8fbfc] nunito mr-[10px]"
        onClick={handleCreatePismo}
      >
        Objavi pismo
      </button>
      <button
        className="bordered-btn  pt-[10px] pb-[10px] pr-[20px] pl-[20px] nunito"
        onClick={handleLogout}
      >
        Logout
      </button>
    </div>
  );
}

export default AdminNav;
