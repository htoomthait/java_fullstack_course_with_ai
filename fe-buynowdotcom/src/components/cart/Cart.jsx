import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useParams } from 'react-router-dom';
import { getUserCart } from "../../store/features/cartSlice"

const Cart = () => {
    const { userId } = useParams();
    const dispatch = useDispatch();
    const cart = useSelector((state) => state.cart)
    const cartId = useSelector((state) => state.cart.cartId)
    const isLoading = useSelector((state) => state.cart.isLoading)

    useEffect(() => {
        dispatch(getUserCart(userId));
    }, [dispatch, userId])

    console.log("The user cart from cart component: ", cart)

    return (
        <div className="container mt-5 mb-5 p-5">
            <table>
                <thead>
                    <tr>
                        <th>Product Image</th>
                        <th>Product Name</th>
                        <th>Brand</th>
                        <th>Unit Price</th>
                        <th>Quantity</th>
                        <th>Total</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
        </div>
    )
}

export default Cart