import { useState } from 'react'
import { Route, RouterProvider, createBrowserRouter, createRoutesFromElements } from 'react-router-dom'
import './App.css'
import RootLayout from './components/layout/RootLayout'
import Home from './pages/Home'
import Products from './pages/Products'
import ProductDetails from './pages/ProductDetails'
import Cart from './components/cart/Cart'

function App() {

  const router = createBrowserRouter(
    createRoutesFromElements(
      <Route path='/' element={<RootLayout />}>

        <Route index path='/' element={<Home />} />
        <Route path='/home' element={<Home />} />
        <Route path='/products' element={<Products />} />
        <Route path='/products/:name' element={<Products />} />

        <Route
          path='/product/:productId/details'
          element={<ProductDetails />}
        />
        <Route
          path="/product/category/:categoryId/products/"
          element={<Products />}
        />
        <Route
          path="/user/:userId/my-cart"
          element={<Cart />}
        />
        <Route path='*' element={<div>404 Not Found</div>} />
      </Route>
    )
  )

  return (
    <RouterProvider router={router} />


  )
}

export default App
