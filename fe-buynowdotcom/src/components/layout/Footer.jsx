import React from 'react'
import { FaFacebookF, FaTwitter, FaInstagram } from "react-icons/fa";

const Footer = () => {
    return (
        <footer className="mega-footer">
            <div className="footer-container">

                <div className="footer-section">
                    <h3> Category</h3>
                    <ul>
                        <li>Category 1</li>
                        <li>Category 2</li>
                        <li>Category 3</li>
                    </ul>
                </div>

                <div className="footer-section">
                    <h3>Contact</h3>
                    <p>Email: info@dcwdshops.com</p>
                    <p>Phone: (123) 456-7890</p>
                </div>

                <div className="footer-section">
                    <h3>Follow Us</h3>
                    <div className='social-icons'>
                        <a
                            href='https://facebook.com'
                            target='_blank'
                            rel='noopener noreferrer'>
                            <FaFacebookF />
                        </a>
                        <a
                            href='https://twitter.com'
                            target='_blank'
                            rel='noopener noreferrer'>
                            <FaTwitter />
                        </a>
                        <a
                            href='https://instagram.com'
                            target='_blank'
                            rel='noopener noreferrer'>
                            <FaInstagram />
                        </a>
                    </div>
                </div>
            </div>
        </footer>
    )
}

export default Footer