import React from 'react'
import { BsDash, BsPlus } from "react-icons/bs";
import { useDispatch, useSelector } from "react-redux";
import { decreaseQuantity, increaseQuantity } from "../../store/features/productSlice"


const QuantityUpdater = ({ disabled, quantity, onDecrease, onIncrease }) => {



    return (
        <>
            <section style={{ width: "150px" }}>
                <div className="input-group">
                    <button
                        disabled={disabled}
                        onClick={onDecrease}
                        className="btn btn-outline-secondary">
                        <BsDash />
                    </button>

                    <input
                        type="number"
                        className="form-control text-center"
                        value={quantity}
                        readOnly
                        disabled={disabled}
                        name="quantity"
                    />

                    <button
                        disabled={disabled}
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