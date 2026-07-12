import { useState } from 'react'
import { Route, RouterProvider, createBrowserRouter, createRoutesFromElements } from 'react-router-dom'
import './App.css'
import RootLayout from './components/layout/RootLayout'
import Home from './pages/Home'
import Products from './components/common/product/Products'

function App() {

  const router = createBrowserRouter(
    createRoutesFromElements(
      <Route path='/' element={<RootLayout />}>

        <Route index path='/' element={<Home />} />
        <Route path='/home' element={<Home />} />
        <Route path='/products' element={<Products />} />
        <Route path='*' element={<div>404 Not Found</div>} />
      </Route>
    )
  )

  return (
    <RouterProvider router={router} />


  )
}

export default App
