
function Header() {
    return <>
        <div className="w-[100%] h-[200px] flex justify-center items-center">
            <div className="w-[100%] flex flex-row justify-between items-center">
                <div className="ml-[30px] flex justify-center items-center">
                    <a href="https://www.alims.gov.rs/" className="flex items-center no-underline ">
                    <img src="/images/alims.png" alt="alims-logo" />
                    <div className="ml-[20px] flex">
                        <p className="text-2xl">alims</p>
                        <p className="text-base">Agencija za lekove i medicinska sredstva Srbije</p>
                    </div>
                    
                    </a>
                </div>
                <div className="flex flex-row">
                    <div>
                        <input type="text" />
                    </div>
                    <div>Serb gov</div>
                    </div>
            </div>
            
        </div>
    </>
}

export default Header