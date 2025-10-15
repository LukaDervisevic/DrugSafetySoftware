import NavBar from "./NavBar"
import SearchBar from "./SearchBar"
import Posts from "./Posts"
import Info from "./Info"

function Main() {
  
  return (
    <div className=' w-[80%] h-[90%] flex flex-row '>
      <div className="bordered w-[75%] h-[100%] bg-[#f9fafc] rounded-2xl flex flex-col items-center">
        <div className="bg-[#f9fafc] w-[100%] h-[80px]">
          <NavBar/>
        </div>
          <SearchBar/>
          <Posts/>
      </div>
      <Info/>
    </div>
  )
}

export default Main