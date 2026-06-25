import React from 'react'
import { Outlet } from 'react-router-dom'
import MainNavBar from './MainNavBar'
import Footer from './Footer'

const RootLayout = () => {
    return (
        <main>
            <MainNavBar />
            <div>
                <Outlet />
            </div>
            <Footer />
        </main>
    )
}

export default RootLayout