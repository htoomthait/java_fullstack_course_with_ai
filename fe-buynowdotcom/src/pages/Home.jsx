import React, { useState, useEffect } from 'react'
import Hero from '../components/hero/Hero'
import Paginator from '../components/common/Paginator';
import { Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import ProductImage from '../components/utils/ProductImage';
import { toast, ToastContainer } from 'react-toastify';
import { useSelector, useDispatch } from 'react-redux';
import ProductCard from '../components/product/ProductCard';
import { setTotalItems } from "../store/features/paginationSlice";
import { getDistinctProductsByName } from "../store/features/productSlice";
import LoadSpinner from '../components/common/LoadSpinner';

const Home = () => {
    const [filteredProducts, setFilteredProducts] = useState([]);
    const { searchQuery, selectedCategory } = useSelector((state) => state.search);
    const { itemsPerPage, totalItems, currentPage } = useSelector((state) => state.pagination)

    const {
        isLoadingGetAllProducts,
        isLoadingGetAllBrands,
        isLoadingGetAllDistinctProductByName,
        distinctProducts
    } = useSelector((state) => state.product);

    const [errorMessage, setErrorMessage] = useState(null);
    const dispatch = useDispatch();


    useEffect(() => {
        dispatch(getDistinctProductsByName());
    }, [dispatch]);


    useEffect(() => {
        const results = distinctProducts.filter(product => {
            const matchesQuery = product.name
                .toLowerCase()
                .includes(searchQuery.toLowerCase());

            const matchesCategory =
                selectedCategory === "all" ||
                product.category.name
                    .toLowerCase()
                    .includes(selectedCategory.toLowerCase());



            return matchesQuery && matchesCategory;
        })



        setFilteredProducts(results);
    }, [distinctProducts, searchQuery, selectedCategory]);

    useEffect(() => {
        dispatch(setTotalItems(filteredProducts.length));
    }, [filteredProducts, dispatch]);




    const indexOfLastProduct = currentPage * itemsPerPage;
    const indexOfFirstProduct = indexOfLastProduct - itemsPerPage;
    const currentProducts = filteredProducts.slice(indexOfFirstProduct, indexOfLastProduct);

    const isLoading =
        isLoadingGetAllProducts ||
        isLoadingGetAllBrands ||
        isLoadingGetAllDistinctProductByName;


    const mainContent = <>
        <Hero />
        <div className="d-flex flex-wrap justify-content-center p-5">
            <ToastContainer />
            {currentProducts.map((product) => (
                <Card key={product.id} className='home-product-card'>
                    <Link to={`products/${product.name}`} className="link">
                        <div className="image-container">
                            {product.images.length > 0 && (
                                <ProductImage imageId={product.images[0].id} />
                            )}
                        </div>
                    </Link>
                    <Card.Body>
                        <p className="produt-description"> {product.name} - {product.description}</p>

                        <h4 className="price">${product.price.toFixed(2)}</h4>

                        <p className="text-success"> {product.inventory} in stock</p>

                        <Link to={`products/${product.name}`} className="shop-now-button"> Shop Now</Link>

                    </Card.Body>
                </Card>
            )
            )}



        </div>
        <Paginator />
    </>


    return (
        <>
            {isLoading && <LoadSpinner />}

            {mainContent}

        </>

    )
}

export default Home