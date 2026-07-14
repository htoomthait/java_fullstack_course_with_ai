import React, { useEffect } from 'react'
import { Pagination } from 'react-bootstrap';
import { setItemsPerPage, setCurrentPage, setTotalItems } from "../../store/features/paginationSlice";
import { useDispatch, useSelector } from 'react-redux';

const Paginator = () => {
    const dispatch = useDispatch();
    const { itemsPerPage, totalItems, currentPage } = useSelector((state) => state.pagination)

    let active = currentPage;
    let items = [];



    const paginate = (pageNumber) => {
        dispatch(setCurrentPage(pageNumber));
    }




    for (let number = 1; number <= Math.ceil(totalItems / itemsPerPage); number++) {
        items.push(
            <Pagination.Item
                key={number}
                active={number === active}
                onClick={() => paginate(number)}
            >
                {number}
            </Pagination.Item>
        );
    }

    return (
        <div className='d-flex justify-content-center me-5'>
            <Pagination>
                {items}
            </Pagination>
        </div>
    )
}

export default Paginator