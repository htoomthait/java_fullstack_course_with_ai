import React, { useState, useEffect } from 'react'
import Hero from '../components/common/hero/Hero'
import Paginator from '../components/common/Paginator';
import { Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import ProductImage from '../components/common/utils/ProductImage';
import { toast, ToastContainer } from 'react-toastify';
import { getDistinctProductsByName } from "../components/services/ProductSerivce";
import { useSelector } from 'react-redux';
// import { useSelector } from "react-redux";

const Home = () => {
    const [products, setProducts] = useState([]);
    const [filteredProducts, setFilteredProducts] = useState([]);
    const { searchQuery, selectedCategory } = useSelector((state) => state.search);

    const [errorMessage, setErrorMessage] = useState(null);
    const [currentPage, setCurrentPage] = useState(1);
    const itemsPerPage = 10;

    useEffect(() => {
        const getProducts = async () => {
            try {
                const response = await getDistinctProductsByName();
                setProducts(response.data);

            } catch (error) {
                console.error("Error fetching products:", error);
                setErrorMessage(error.message);
                toast.error(error.message);
            }
        };

        getProducts();
    }, [])


    useEffect(() => {
        const results = products.filter(product => {
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
    }, [products, searchQuery, selectedCategory]);





    const paginate = (pageNumber) => setCurrentPage(pageNumber);
    const indexOfLastProduct = currentPage * itemsPerPage;
    const indexOfFirstProduct = indexOfLastProduct - itemsPerPage;
    const currentProducts = filteredProducts.slice(indexOfFirstProduct, indexOfLastProduct);


    return (
        <>

            <>
                <Hero />
                <div className="d-flex flex-wrap justify-content-center p-5">
                    <ToastContainer />
                    {currentProducts.map((product) => (
                        <Card key={product.id} className='home-product-card'>
                            <Link to={"#"} className="link">
                                <div className="image-container">
                                    {product.images.length > 0 && (
                                        <ProductImage productId={product.images[0].id} />
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
                <Paginator
                    itemPerPage={itemsPerPage}
                    totalItems={filteredProducts.length}
                    currentPage={currentPage}
                    paginate={paginate}


                />
            </>
        </>

    )
}

export default Home