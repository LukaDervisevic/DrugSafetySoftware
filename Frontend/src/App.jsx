import './styles/App.css'
import InfoNav from './components/InfoNav'
import Header from './components/Header'
import NavBar from './components/NavBar'

function App() {

  return (
    <>
      <div className='bg-[#f9fafc] w-[80%] h-[100%] flex flex-col justify-start items-center'>
        <InfoNav />
        <Header />
        <NavBar />
      </div>
    </>
  )
}

export default App
