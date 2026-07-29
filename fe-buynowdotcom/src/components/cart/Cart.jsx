import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Link, useParams } from 'react-router-dom';
import { getUserCart, updateQuantity, removeItemFromCart } from "../../store/features/cartSlice"
import { Button, Card } from 'react-bootstrap';
import ProductImage from '../utils/ProductImage';
import QuantityUpdater from '../utils/QuantityUpdater';
import { MdOutlineRemoveShoppingCart } from "react-icons/md";
import { toast, ToastContainer } from 'react-toastify';
import LoadSpinner from '../common/LoadSpinner';
import { placeOrder } from '../../store/features/orderSlice';

const Cart = () => {
    const { userId } = useParams();
    const dispatch = useDispatch();
    const cart = useSelector((state) => state.cart)
    const cartId = useSelector((state) => state.cart.cartId)
    const isLoading = useSelector((state) => state.cart.isLoading)

    const order = useSelector((state) => state.order)

    useEffect(() => {
        dispatch(getUserCart(userId));
    }, [dispatch, userId])


    useEffect(() => {
        console.log("The user cart from cart component: ", cart)
    }, [cart])

    const handleDecreaseQuantity = (itemId) => {
        const item = cart.items.find((item) => item.product.id === itemId);
        if (item && item.quantity > 1) {
            dispatch(updateQuantity({
                cartId, itemId, newQuantity: item.quantity - 1
            }));
        }
    }

    const handleIncreaseQuantity = (itemId) => {
        const item = cart.items.find((item) => item.product.id === itemId);
        if (item && cartId) {
            dispatch(updateQuantity({
                cartId, itemId, newQuantity: item.quantity + 1
            }));
        }
    }

    const handleRemoveItem = async (itemId) => {
        try {
            await dispatch(removeItemFromCart({ cartId, itemId })).unwrap();
            console.info(`Item removed! cartId: ${cartId}, itemId: ${itemId}`);
            toast.success("Item removed from cart");
        } catch (error) {
            toast.error(error?.message || error);
        }
    };

    const handlePlaceOrder = async () => {
        if (cart.items.length === 0) {
            toast.error("Your cart is empty.");
            return;
        }
        try {
            const result = await dispatch(placeOrder(userId)).unwrap();
            console.log("From success order place result", result);
            toast.success(result.message);
            dispatch(getUserCart(userId));

        } catch (error) {
            console.log("Place order error:", error);

            toast.error(
                typeof error === "string"
                    ? error
                    : error?.message || "Failed to place order"
            );
        }
    }

    if (isLoading) {
        return <LoadSpinner />
    }

    return (
        <div className="container mt-5 mb-5 p-5">
            <ToastContainer />
            <div>
                <div className="d-flex justify-content-between mb-4 fw-bold">
                    <div className="text-center">Image</div>
                    <div className="text-center">Name</div>
                    <div className="text-center">Brand</div>
                    <div className="text-center">Price</div>
                    <div className="text-center">Quantity</div>
                    <div className="text-center">Total Price</div>
                    <div className="text-center">Action</div>
                </div>
                <hr className="mb-2 mt-2" />
                <h3 className='mb-4 cart-title'>My Shopping Cart</h3>

                {cart.items.map((item, index) => (
                    <Card key={index} className='mb-4'>
                        <Card.Body className='d-flex justify-content-between align-items-center shadow'>
                            <div className='d-flex align-items-center'>
                                <Link to={"#"}>
                                    <div className='cart-image-container'>
                                        {item.product.images.length > 0 && (
                                            <ProductImage imageId={item.product.images[0].id} />
                                        )}
                                    </div>
                                </Link>

                            </div>

                            <div className='text-center'>{item.product.name}</div>
                            <div className='text-center'>{item.product.brand}</div>
                            <div className='text-center'>
                                ${item.product.price.toFixed(2)}
                            </div>
                            <div className='text-center'>
                                <QuantityUpdater
                                    quantity={item.quantity}
                                    onDecrease={() => handleDecreaseQuantity(item.product.id)}
                                    onIncrease={() => handleIncreaseQuantity(item.product.id)}
                                />
                            </div>
                            <div className='text-center'>${item.totalPrice.toFixed(2)}</div>
                            <div>
                                <Button
                                    className="btn btn-danger"
                                    onClick={() => handleRemoveItem(item.product.id)}>
                                    <span className='remove-item'>
                                        <MdOutlineRemoveShoppingCart />

                                    </span>
                                </Button>
                            </div>
                        </Card.Body>
                    </Card>
                ))}

                <hr />
                <div className='cart-footer d-flex align-items-center mt-4'>
                    <h4 className='mb-0 cart-title'>
                        Total Cart Amount: ${cart.totalAmount.toFixed(2)}
                    </h4>
                    <div className='ms-auto checkout-links'>
                        <Link to={"/products"}>Continue Shopping</Link>
                        <Link to={"#"} onClick={handlePlaceOrder}>Proceed to Checkout</Link>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Cart