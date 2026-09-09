import React, { useEffect, useState } from 'react'
import ProductImage from '../utils/ProductImage'
import { Card } from 'react-bootstrap'
import { Link } from 'react-router-dom'
import StockStatus from '../utils/StockStatus'
import { deleteProductById } from '../../store/features/productSlice'
import { useDispatch } from 'react-redux'
import { toast } from 'react-toastify'
import ProductDeleteConfirmBox from './ProductDeleteConfirmBox'


const ProductCard = ({ products }) => {

    const dispatch = useDispatch();
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [productToDelete, setProductToDelete] = useState(null);

    const handleDeleteProduct = async (productId) => {
        setProductToDelete(productId);
        setShowDeleteModal(true);
    };

    const confirmDelete = async () => {
        try {
            console.log("Deleting product with ID:", productToDelete);
            const result = await dispatch(deleteProductById(productToDelete)).unwrap();

            setShowDeleteModal(false);
            setProductToDelete(null);

            toast.success(result.message);
        } catch (error) {
            toast.error(error.message);
        }
    }




    return (
        <>
            <main className="row m-2">
                {products.map((product) => (
                    <div className='col-12 col-sm-6 col-md-4 col-lg-2' key={product.id}>
                        <Card className='mb-2 mt-2' style={{ height: '475px', maxHeight: '480px' }}>
                            <Link to={`/product/${product.id}/details`} className='link'>
                                <div className="image-container">
                                    {(product.images ?? []).length > 0 && (
                                        <ProductImage
                                            imageId={product.images[0].id}
                                        />
                                    )}
                                </div>
                            </Link>
                            <Card.Body>
                                <p className='product-description'>
                                    {product.name} - {product.description}
                                </p>
                                <h4 className='price'>${product.price}</h4>
                                <p>
                                    <StockStatus inventory={product.inventory} />
                                </p>
                                <div className='d-flex gap-2'>
                                    <Link to='#' onClick={() => handleDeleteProduct(product.id)}>
                                        delete
                                    </Link>
                                    <Link to={`/update-product/${product.id}/update`}>edit</Link>
                                    <button className='shop-now-button'>Add to cart</button>
                                </div>

                            </Card.Body>
                        </Card>
                    </div>
                ))}



            </main>
            <ProductDeleteConfirmBox
                showDeleteModal={showDeleteModal}
                setShowDeleteModal={setShowDeleteModal}
                confirmDelete={confirmDelete}
                productToDelete={productToDelete}
            />
        </>

    )
}

export default ProductCard