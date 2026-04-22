import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const [count, setCount] = useState(0)

  const incrementCount = () => {
    setCount(count + 5)
  }

  const decrementCount = () => {
    setCount(count - 5)
  }

  const handleButtonClick = () => {
    console.log("Button Clicked!")
  }

  return (
    <>
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>My first react app</h1>
      <div className="card">
        <button onClick={incrementCount} className=''>
          count ++5
        </button>



        <button onClick={decrementCount}>
          count --5
        </button>
        <button onClick={handleButtonClick}> Click Me </button>
        <p>Count is: {count}</p>
        <p>
          Edit <code>src/App.jsx</code> and save to test HMR
        </p>
      </div>

      <input type="text" placeholder="Type here..." onMouseEnter={() => console.log("Text Fields Entered!")} />

      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  )
}

export default App
