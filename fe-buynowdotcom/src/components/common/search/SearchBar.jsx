import React, { useEffect, useState } from 'react';
import { getAllCategories } from '../../../store/features/categorySlice';
import { useDispatch, useSelector } from "react-redux";

const SearchBar = ({
    value,
    onChange,
    onCategoryChange,
    onClearFilter
}) => {
    const [category, setCategory] = useState([]);
    const dispatch = useDispatch();
    const { categories = [] } = useSelector((state) => state.category);
    const { searchQuery, selectedCategory } = useSelector(
        (state) => state.search
    );

    const handleCategoryChange = (e) => {
        onCategoryChange(e.target.value);
    }




    // Fetch categories from the backend when the component mounts
    useEffect(() => {
        dispatch(getAllCategories());
    }, [dispatch]);


    return (
        <>
            <div className="search-bar input-group input-group-sm">
                <select className="form-control-sm" name="category" id="category"
                    value={selectedCategory}
                    onChange={handleCategoryChange}
                >
                    <option value="all">All Category</option>
                    {categories.map((category) => (
                        <option key={category.id} value={category.name}>
                            {category.name}
                        </option>
                    ))}
                </select>

                <input
                    type="text"
                    className="form-control-sm"
                    value={searchQuery}
                    onChange={onChange}
                    placeholder="Search for products...(e.g. tv)" />

                <button className="search-button btn btn-primary btn-sm" onClick={onClearFilter}>
                    Clear Filter
                </button>
            </div>
        </>
    )
}

export default SearchBar