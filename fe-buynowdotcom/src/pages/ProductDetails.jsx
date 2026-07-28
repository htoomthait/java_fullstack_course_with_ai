import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import ProductImage from '../components/utils/ProductImage';
import { getProductById, setQuantity } from '../store/features/productSlice';
import ImageZoomify from '../components/common/ImageZoomify';
import QuantityUpdater from '../components/utils/QuantityUpdater';
import { FaCartPlus } from "react-icons/fa";
import { capitalizeRegex } from '../components/utils/StringFunc';
import { addToCart } from '../store/features/cartSlice';
import { toast, ToastContainer } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";

const ProductDetails = () => {
    const { productId } = useParams();
    const dispatch = useDispatch();
    const { product, quantity } = useSelector((state) => state.product)
    const { successMessage, errorMessage } = useSelector((state) => state.cart)

    useEffect(() => {
        dispatch(getProductById(productId));
    }, [dispatch, productId])


    const handleAddToCart = () => {

        try {
            dispatch(addToCart({ productId, quantity }));
            toast.success(successMessage)
        } catch (error) {
            if (errormessage) {
                toast.error(errorMessage)
            }
            else {
                toast.error(error.message);
            }

        }
    }

    const handleIncreaseQuantity = () => {
        dispatch(setQuantity(quantity + 1));
    };

    const handleDecreaseQuantity = () => {
        if (quantity > 1) {
            dispatch(setQuantity(quantity - 1));
        }

    };



    return (
        <>
            <div className="container">
                <ToastContainer />
                {product ? (
                    <div className='row product-details'>
                        <div className="col-md-2">
                            Product Image <br /> <br />


                            {
                                product.images.map((img, index) => (

                                    <div key={index} className="image-container">
                                        <ImageZoomify imageId={img.id} />
                                    </div>

                                ))
                            }

                        </div>
                        <div className="col-md-8 details-container">
                            <h1 className='product-name'>{product.name}</h1>
                            <h4 className='price'>${product.price}</h4>
                            <p className='product-description'>{product.description}</p>
                            <p className='product-name'>Brand: {capitalizeRegex(product.brand)}</p>
                            <p className='product-name'>
                                Rating: <span className='rating'>some stars</span>
                            </p>
                            <p>
                                {" "}
                                {product.inventory > 0 ? (
                                    <span className='text-success'>
                                        {product.inventory} in stock
                                    </span>
                                ) : (
                                    <span className='text-danger'>Out of stock</span>
                                )}
                            </p>
                            <p>Quantity:</p>
                            <QuantityUpdater
                                quantity={quantity}
                                onIncrease={handleIncreaseQuantity}
                                onDecrease={handleDecreaseQuantity}
                            />
                            <div className="d-flex gap-2 mt-3">
                                <button className="add-to-cart-button" onClick={() => handleAddToCart()}>
                                    <FaCartPlus /> Add to cart
                                </button>
                                <button className="buy-now-button">Buy now</button>
                            </div>
                        </div>

                    </div>
                ) : (<p>No products</p>)
                }
            </div>
        </>
    )
}

export default ProductDetails

