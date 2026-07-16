import React, { useState, useEffect } from 'react';
import ProductCard from "../components/product/ProductCard";
import SearchBar from '../components/search/SearchBar';
import { getAllProducts } from '../store/features/productSlice';
import { useDispatch, useSelector } from "react-redux";
import { useLocation, useParams } from 'react-router-dom';
import LoadSpinner from '../components/common/LoadSpinner';
import SideBar from '../components/common/SideBar';
import Paginator from '../components/common/Paginator';
import { setTotalItems } from "../store/features/paginationSlice";
import { setInitialSearchQuery } from "../store/features/searchSlice"





const Products = () => {
    const [filteredProducts, setFilteredProducts] = useState([]);
    const dispatch = useDispatch();
    const { searchQuery, selectedCategory } = useSelector(
        (state) => state.search
    );
    const {
        isLoadingGetAllProducts,
        isLoadingGetAllBrands,
        isLoadingGetAllDistinctProductByName,
        products,
        selectedBrands
    } = useSelector((state) => state.product);
    const { itemsPerPage, totalItems, currentPage } = useSelector((state) => state.pagination)



    useEffect(() => {
        dispatch(getAllProducts());
    }, [dispatch]);


    const { name } = useParams();
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const initialSearchQuery = queryParams.get("search") || name || "";


    useEffect(() => {
        dispatch(setInitialSearchQuery(initialSearchQuery));
    }, [initialSearchQuery, dispatch]);





    useEffect(() => {
        const results = products.filter((product) => {
            const matchesQuery = product.name
                .toLowerCase()
                .includes(searchQuery.toLowerCase());

            const matchesCategory =
                selectedCategory === "all" ||
                product.category.name
                    .toLowerCase()
                    .includes(selectedCategory.toLowerCase());


            const matchesBrand =
                selectedBrands.length === 0 ||
                selectedBrands.some((selectedBrand) =>
                    product.brand.toLowerCase().includes(selectedBrand.toLowerCase())
                );

            return matchesQuery && matchesCategory && matchesBrand;
        })



        setFilteredProducts(results);
    }, [searchQuery, selectedCategory, products, selectedBrands])

    useEffect(() => {
        dispatch(setTotalItems(filteredProducts.length));
    }, [filteredProducts, dispatch]);



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

                <section style={{ flex: 1, }}>
                    <ProductCard products={currentProducts} />
                </section>


            </div>
            <Paginator />
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