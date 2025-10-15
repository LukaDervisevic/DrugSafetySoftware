import { useNavigate } from "react-router-dom"

function NavBar() {
    const navigate = useNavigate()
    const handleLoginClick = () => {
    navigate("/login")
  }

    return (
        <div className="h-[100%] flex flex-row justify-between items-center">
            <div className="ml-[50px] nunito text-2xl">Alims</div>
            <div className="mr-[50px] flex flex-row items-center">
                <a href="#" className="nunito text-2xl no-underline flex flex-row items-center">
                <span className="mr-[10px] text-[#292b2c]">Treba vam pomoc ?</span>
                <img className="mr-[30px]" src="/images/chat.png" alt="chat-icon" />
                </a>
            <button className="bordered-btn nunito pt-[10px] pb-[10px] pl-[20px] pr-[20px]"
            onClick={handleLoginClick}>Login</button>
            </div>
        </div>
    )
}

export default NavBar