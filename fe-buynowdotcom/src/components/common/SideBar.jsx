import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getAllBrands, filterByBrands } from '../../store/features/productSlice';

const SideBar = () => {

    const dispatch = useDispatch();
    const { brands, selectedBrands } = useSelector((state) => state.product)

    useEffect(() => {

        dispatch(getAllBrands());
    }, [dispatch]);



    const handleBrandChange = (brand, isChecked) => {
        console.log("Brand to change", brand, "Value to update", isChecked);
        dispatch(filterByBrands({ brand, isChecked }));
    }

    return (
        <>
            <h6>Filter by brand</h6>
            <ul className="brand-list">
                {brands.map((brand, index) => (
                    <li key={index} className="brand-item">
                        <label className="checkbox-container">
                            <input
                                type="checkbox"
                                checked={selectedBrands.includes(brand.brand)}
                                onChange={(e) => { handleBrandChange(brand.brand, e.target.checked) }}

                            />
                            <span className="checkmark"></span>
                            {brand.brand}
                        </label>
                    </li>
                ))}


            </ul>
        </>
    )
}

export default SideBar