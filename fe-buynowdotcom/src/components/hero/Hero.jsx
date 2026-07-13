import React, { useState } from 'react'
import HeroSlider from './HeroSlider';
import SearchBar from '../search/SearchBar';
import { setSearchQuery, setSelectedCategory, clearFilters } from '../../store/features/searchSlice';
import { useDispatch } from 'react-redux';



const Hero = () => {
    const [currentSlide, setCurrentSlide] = useState(0);

    const dispatch = useDispatch();


    return (
        <div className="hero">
            <HeroSlider currentSlide={currentSlide} setCurrentSlide={setCurrentSlide} />
            <div className="hero-content">
                <h1>Welcome to <span className="text-primary">buyNow</span>.com</h1>

                <SearchBar
                    onChange={(e) => dispatch(setSearchQuery(e.target.value))}
                    onCategoryChange={(category) => dispatch(setSelectedCategory(category))}
                    onClearFilter={() => dispatch(clearFilters())}
                />

                <div className="home-button-container">
                    <a href="#" className="home-shop-button link">
                        Shop Now
                    </a>
                    <button className="deals-button">Today's Deal</button>
                </div>
            </div>

        </div>
    )
}

export default Hero