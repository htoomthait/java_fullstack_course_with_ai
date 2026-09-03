import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getAllBrands, addBrand } from '../../store/features/productSlice'

const BrandSelector = ({
    selectedBrand,
    onBrandChange,
    newBrand,
    showNewBrandInput,
    setNewBrand,
    setShowNewBrandInput
}) => {
    const dispatch = useDispatch();
    const brands = useSelector((state) => state.product.brands)

    useEffect(() => {
        dispatch(getAllBrands())

    }, [])

    useEffect(() => {
        console.log("all brands for selectors", brands)
    }, [brands])

    const handleAddNewBrand = () => {

        if (newBrand !== undefined && newBrand !== '') {
            dispatch(addBrand({ brand: newBrand }))
            onBrandChange(newBrand)
            setShowNewBrandInput(false)
            setNewBrand('')
        }
    }

    const handleBrandChange = (e) => {
        if (e.target.value === 'New') {
            setShowNewBrandInput(true);
        } else {
            setShowNewBrandInput(false)
            onBrandChange(e.target.value)
        }
    }

    const handleNewBrandChange = (e) => {
        setNewBrand(e.target.value);
    }

    return (
        <>
            <div className="mb-3">
                <label className="form-label">Brand</label>
                <select
                    className="form-select"
                    required
                    value={selectedBrand}
                    onChange={handleBrandChange}

                >
                    <option value="">All Brands</option>
                    <option value="New">Add New Brand</option>
                    {brands.map((brand, index) => (
                        <option key={index} value={brand.brand}>
                            {brand.brand}
                        </option>
                    ))}
                </select>
                {showNewBrandInput && (
                    <div className="input-group">
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Enter new brand name"
                            value={newBrand}
                            onChange={handleNewBrandChange}
                        />
                        <button
                            type="button"
                            className="btn btn-secondary btn-sm"
                            onClick={handleAddNewBrand}>
                            Add Brand
                        </button>
                    </div>
                )}
            </div>
        </>
    )
}

export default BrandSelector