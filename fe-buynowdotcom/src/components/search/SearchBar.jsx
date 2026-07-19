import React, { useEffect, useState } from 'react';
import { getAllCategories } from '../../store/features/categorySlice';
import { useDispatch, useSelector } from "react-redux";
import { setSearchQuery, setSelectedCategory, clearFilters } from '../../store/features/searchSlice';
import { useNavigate, useParams } from 'react-router-dom';

const SearchBar = ({
    value,
    onChange,
    onClearFilter
}) => {
    const [category, setCategory] = useState([]);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const { categories = [] } = useSelector((state) => state.category);
    const { searchQuery, selectedCategory } = useSelector(
        (state) => state.search
    );
    const { categoryId } = useParams();

    const handleSearchQuery = (e) => {
        dispatch(setSearchQuery(e.target.value));
    }

    const handleCategoryChange = (e) => {
        dispatch(setSelectedCategory(e.target.value));
    }

    const handleClearFilters = (e) => {
        dispatch(clearFilters());
        navigate("/products");
    }

    useEffect(() => {
        if (categoryId && categories.length > 0) {
            const seletedCategory = categories.find(
                (category) => category.id === parseInt(categoryId, 10)
            );

            if (seletedCategory) {
                dispatch(setSelectedCategory(seletedCategory.name));
            } else {
                dispatch(setSelectedCategory("all"));
            }
        }
    }, [categoryId, categories, dispatch]);



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
                    onChange={handleSearchQuery}
                    placeholder="Search for products...(e.g. tv)" />

                <button className="search-button btn btn-primary btn-sm" onClick={handleClearFilters}>
                    Clear Filter
                </button>
            </div>
        </>
    )
}

export default SearchBar