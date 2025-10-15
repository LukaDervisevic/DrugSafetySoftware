
function Post({pismo}) {
    return (
        <div className="bordered bg-[#fefefe] w-[80%] min-h-[150px] mt-[20px] flex flex-row">
              <div className="nunito w-[75%] flex flex-col ">
                  <span className="ml-[20px] mt-[20px]">{pismo.naslov}</span>
                  <span className="ml-[20px] mt-[10px] pb-[20px] pr-[10px]">{pismo.sadrzaj}</span>
              </div>
              <hr class="border-t border-gray-300 my-4 opacity-40" />
              <div className="flex-1 flex flex-col  justify-center items-center nunito">
                    <div className="bordered bg-[#e6f2f5] p-[10px] mb-[10px]">
                        <span>{pismo.datum}</span>
                    </div>
                    <button className="bordered-btn post-btn nunito bg-[#009fac] text-[#f8fbfc]"
                    >Pismo</button>
              </div>
        </div>
    )
}

export default Post