import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import LoadSpinner from '../common/LoadSpinner';
import { Link, useParams } from 'react-router-dom';
import { fetchUserOrder } from '../../store/features/orderSlice';
import { toast, ToastContainer } from 'react-toastify';

const Order = () => {
    const dispatch = useDispatch();


    const { userId } = useParams();
    const orders = useSelector((state) => state.order.orders);
    const isLoading = useSelector((state) => state.order.isLoading);


    useEffect(() => {

        try {
            dispatch(fetchUserOrder(userId));
        } catch (error) {
            toast.error(error)

        }
    }, [dispatch, userId]);

    if (isLoading) {
        return <LoadSpinner />
    }


    return (
        <div className="container mt-5">
            <ToastContainer />
            <div className="row">
                <div className="col-6">
                    <h3 className="mb-4 cart-title">My Order History</h3>
                </div>
            </div>
            <div className="row">
                <div className="col-12">
                    {orders.length == 0 ?
                        (<>
                            <p>No orders found at the moment</p>
                        </>) :
                        (<>
                            <table className='table' >
                                <thead>
                                    <tr>
                                        <th>Order Id</th>
                                        <th>Date </th>
                                        <th>Total Amount</th>
                                        <th>Status</th>
                                        <th>Items</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {orders.map((order, index) => (
                                        <tr key={index}>
                                            <td>{order.id}</td>
                                            <td>{new Date(order.orderDate).toLocaleDateString()}</td>
                                            <td>$ {order.totalAmount.toFixed(2)}</td>
                                            <td>{order.status}</td>
                                            <td>
                                                <table className="table table-sm table-bordered table-striped table-hover">
                                                    <thead>
                                                        <tr>
                                                            <th>Item ID</th>
                                                            <th>Name</th>
                                                            <th>Brand</th>
                                                            <th>Quantity</th>
                                                            <th>Unit Price</th>
                                                            <th>Total Price</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        {order.orderItems.map((item, iIndex) => (
                                                            <tr key={iIndex}>
                                                                <td>{item.productId}</td>
                                                                <td>{item.productName}</td>
                                                                <td>{item.productBrand}</td>
                                                                <td>{item.quantity}</td>
                                                                <td>$ {Number(item.price).toFixed(2)}</td>
                                                                <td>$ {(Number(item.price) * Number(item.quantity)).toFixed(2)}</td>
                                                            </tr>
                                                        ))}
                                                    </tbody>
                                                </table>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>

                        </>

                        )}
                </div>
            </div>

            <div className="mb-2">
                <Link to={"/products"}>Start Shopping</Link>
            </div>
        </div>
    )
}

export default Order