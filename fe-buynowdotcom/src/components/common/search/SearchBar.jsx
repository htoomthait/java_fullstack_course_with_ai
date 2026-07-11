import React, { useEffect, useState } from 'react';
import { getAllCategories } from '../../../store/features/categorySlice';
import { useDispatch, useSelector } from "react-redux";

const SearchBar = ({
    value,
    onChange
}) => {
    const [category, setCategory] = useState([]);
    const dispatch = useDispatch();
    const { categories = [] } = useSelector((state) => state.category);
    const { searchQuery, selectedCategory } = useSelector(
        (state) => state.search
    );






    // Fetch categories from the backend when the component mounts
    useEffect(() => {
        dispatch(getAllCategories());
    }, [dispatch]);


    return (
        <>
            <div className="search-bar input-group input-group-sm">
                <select className="form-control-sm" name="category" id="category">

                    {categories.map((cat) => (
                        <option key={cat.id} value={cat.name}>{cat.name}</option>
                    ))}
                </select>

                <input
                    type="text"
                    className="form-control-sm"
                    value={value}
                    onChange={onChange}
                    placeholder="Search for products...(e.g. tv)" />

                <button className="search-button btn btn-primary btn-sm"> Clear Filter</button>
            </div>
        </>
    )
}

export default SearchBar