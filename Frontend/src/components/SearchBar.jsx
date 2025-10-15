
function SearchBar() {
    return (
        <div className="bordered bg-[#e6f2f5] w-[95%] h-[125px] flex flex-row justify-center items-center">
            <div className="flex flex-row w-[80%] justify-center">
              <div className="w-[80%] mr-[15px]">
                <input type="text" placeholder="Unesite lek" className="bordered search-bar w-[100%] h-[30px] pl-[10px]"/>
              </div>
              <div className="flex-1 ml-[10px]">
                <button className="bordered-btn nunito search-btn bg-[#009fac] text-[#f8fbfc]">Pretraga</button>
              </div>
            </div>
          </div>
    )
}

export default SearchBar