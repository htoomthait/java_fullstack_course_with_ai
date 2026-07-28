import React from 'react'
import { BsDash, BsPlus } from "react-icons/bs";
import { useDispatch, useSelector } from "react-redux";
import { decreaseQuantity, increaseQuantity } from "../../store/features/productSlice"


const QuantityUpdater = ({ quantity, onDecrease, onIncrease }) => {
    //const dispatch = useDispatch();
    // const quantity = useSelector((state) => state.product.quantity)


    return (
        <>
            <section style={{ width: "150px" }}>
                <div className="input-group">
                    <button
                        onClick={onDecrease}
                        className="btn btn-outline-secondary">
                        <BsDash />
                    </button>

                    <input
                        type="number"
                        className="form-control text-center"
                        value={quantity}
                        readOnly
                        name="quantity"
                    />

                    <button
                        onClick={onIncrease}
                        className="btn btn-outline-secondary">
                        <BsPlus />
                    </button>
                </div>

            </section>
        </>
    )
}

export default QuantityUpdater