import React, { useState } from 'react'
import Hero from '../components/common/hero/Hero'
import Paginator from '../components/common/Paginator';
import { Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import ProductImage from '../components/common/utils/ProductImage';

const Home = () => {
    const [products, setProducts] = useState([]);
    const [filteredProducts, setFilteredProducts] = useState([]);




    const [currentPage, setCurrentPage] = useState([]);
    const itemPerPage = 10;


    const paginate = (pageNumber) => setCurrentPage(pageNumber);
    const indexOfLastProduct = currentPage * itemPerPage;
    const indexOfFirstProduct = indexOfLastProduct - itemPerPage;
    const currentProducts = filteredProducts.slice(indexOfFirstProduct, indexOfLastProduct);


    return (
        <>

            <>
                <Hero />
                <div className="d-flex flex-wrap justify-content-center p-5">
                    {products.map((product) => (
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
                    itemPerPage={itemPerPage}
                    totalItems={filteredProducts.length}
                    currentPage={currentPage}
                    paginate={paginate}


                />
            </>
        </>

    )
}

export default Home