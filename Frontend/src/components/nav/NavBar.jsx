import { useContext } from "react";
import { AdminContext } from "../../context/AdminContext";
import VisitorNav from "./VisitorNav";
import AdminNav from "./AdminNav";

function NavBar() {
  const { isLoggedIn } = useContext(AdminContext);

  return (
    <div className="h-[100%] flex flex-row justify-between items-center">
      <div className="ml-[50px] nunito text-2xl">Alims</div>
      {isLoggedIn ? <AdminNav /> : <VisitorNav />}
    </div>
  );
}

export default NavBar;
