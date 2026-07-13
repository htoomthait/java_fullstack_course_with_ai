import React, { useState, useEffect } from 'react';
import ProductCard from "./ProductCard";
import SearchBar from '../search/SearchBar';
import { getAllProducts } from '../../store/features/productSlice';
import { useDispatch, useSelector } from "react-redux";
import { useLocation, useParams } from 'react-router-dom';
import LoadSpinner from '../common/LoadSpinner';
import SideBar from '../common/SideBar';




const Products = () => {
    const [filteredProducts, setFilteredProducts] = useState([]);
    const dispatch = useDispatch();
    const { searchQuery, selectedCategory } = useSelector(
        (state) => state.search
    );
    const { isLoadingGetAllProducts, isLoadingGetAllBrands, isLoadingGetAllDistinctProductByName } = useSelector((state) => state.product);
    const currentPage = 1;
    const itemsPerPage = 10;


    const { name } = useParams();
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const initialSearchQuery = queryParams.get("search") || name || "";



    useEffect(() => {
        dispatch(getAllProducts());
    }, [dispatch]);


    const indexOfLastProduct = currentPage * itemsPerPage;
    const indexOfFirstProduct = indexOfLastProduct - itemsPerPage;
    const currentProducts = filteredProducts.slice(
        indexOfFirstProduct,
        indexOfLastProduct
    );

    const isLoading =
        isLoadingGetAllProducts ||
        isLoadingGetAllBrands ||
        isLoadingGetAllDistinctProductByName;


    const mainContent = <>
        <div>
            <div className="d-flex justify-content-center">
                <div className="col-md-6 mt-2">
                    <div className="search-bar input-group">
                        <SearchBar />
                    </div>
                </div>
            </div>
            <div className="d-flex ">
                <aside className="sidebar" style={{ width: '250px', padding: '1rem' }}>
                    <SideBar />
                </aside>

                <section style={{ flex: 1, padding: '1rem', }}>
                    Products will be displayed here....
                </section>

                <div className="pagination">
                    pagination comming here....
                </div>
            </div>
        </div>
    </>;



    return (
        <>

            {isLoading && <LoadSpinner />}

            {mainContent}




        </>

    )
}

export default Products