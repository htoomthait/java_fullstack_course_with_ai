import React, { useState } from 'react'
import Hero from '../components/common/hero/Hero'
import Paginator from '../components/common/Paginator';
import { Card } from 'react-bootstrap';

const Home = () => {
    const [currentPage, setCurrentPage] = useState([]);
    const itemPerPage = 10;


    const paginate = (pageNumber) => setCurrentPage(pageNumber);
    const indexOfLastProduct = currentPage * itemPerPage;
    const indexOfFirstProduct = indexOfLastProduct - itemPerPage;
    const currentProducts = filteredProducts.slice(indexOfFirstProduct, indexOfLastProduct);


    return (
        <>

            <div>
                <Hero />

                <Card className='home-product-card'>
                    <Link to={"#"}> Product image will be here...</Link>
                    <Card.Body>
                        <p className="produt-description"> product name and description will be here. ...</p>

                        <h4 className="price">product price...</h4>

                        <p className="text-success"> Product inventory in stock</p>

                        <Link to={"#"} className="shop-now-button"> Shop Now</Link>

                    </Card.Body>
                </Card>

                <Paginator
                    itemPerPage={itemPerPage}
                    totalItems={filteredProducts.length}
                    currentPage={currentPage}
                    paginate={paginate}


                />
            </div>
        </>

    )
}

export default Home