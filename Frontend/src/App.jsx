
function App() {

  return (
    <>
      <div className=' w-[80%] h-[90%] flex flex-row '>
        <div className="bordered w-[75%] h-[100%] bg-[#f9fafc] rounded-2xl flex flex-col items-center">
          <div className="bg-[#f9fafc] w-[100%] h-[80px]">
            <div className="h-[100%] flex flex-row justify-between items-center">
              <div className="ml-[50px] nunito text-2xl">Alims</div>
              <div className="mr-[50px] flex flex-row items-center">
                
                  <a href="#" className="nunito text-2xl no-underline flex flex-row items-center">
                    <span className="mr-[10px] text-[#292b2c]">Treba vam pomoc ?</span>
                    <img className="mr-[30px]" src="/images/chat.png" alt="chat-icon" />
                  </a>
                <button className="bordered-btn nunito pt-[10px] pb-[10px] pl-[20px] pr-[20px]">Login</button>
              </div>
            </div>
          </div>
          <div className="bordered bg-[#e6f2f5] w-[95%] h-[125px] flex flex-row justify-center items-center">
            <div className="flex flex-row w-[80%] justify-center">
              <div className="w-[80%] mr-[15px]">
                <input type="text" className="bordered w-[100%] h-[30px] pl-[10px]"/>
              </div>
              <div className="flex-1 ml-[10px]">
                <button className="bordered-btn nunito pt-[10px] pb-[10px] pl-[30px] pr-[30px] bg-[#009fac] text-[#f8fbfc]">Pretraga</button>
              </div>
            </div>
          </div>
          <div className="flex flex-col items-center overflow-y-auto">
            <div className="bordered bg-[#fefefe] w-[80%] h-[150px] mt-[20px] flex flex-row">
              <div className="nunito w-[75%] flex flex-col ">
                  <span className="ml-[20px] mt-[20px]">Lemod-Solu 40 mg/mL</span>
                  <span className="ml-[20px] mt-[10px]">Писмо здравственим радницима о новој формулацији лека Lemod-Solu 40 mg/mL која више не садржи помоћне супстанце лактозу и бензилалкохол</span>
              </div>
              <hr class="border-t border-gray-300 my-4 opacity-40" />
              <div className="flex-1"></div>
            </div>
            <div className="bordered bg-[#fefefe] w-[80%] h-[150px] mt-[20px]">
            </div>
            <div className="bordered bg-[#fefefe] w-[80%] h-[150px] mt-[20px]">
            </div>
            <div className="bordered bg-[#fefefe] w-[80%] h-[150px] mt-[20px]">
            </div>
            <div className="bordered bg-[#fefefe] w-[80%] h-[150px] mt-[20px]">
            </div>
          </div>  
        </div>
        <div className="bordered w-[25%] h-[100%] bg-[#f9fafc] ml-[50px] rounded-2xl"></div>
      </div>
    </>
  )
}

export default App
