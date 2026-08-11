import React from 'react'

const StockStatus = ({ inventory }) => {
    return (
        <>
            {inventory > 0 ? (
                <span className='text-success'>
                    {inventory} in stock
                </span>
            ) : (
                <span className='text-danger'>Out of stock</span>
            )}
        </>
    )
}

export default StockStatus