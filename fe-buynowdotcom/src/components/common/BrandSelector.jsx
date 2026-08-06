import React from 'react'
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

    const handleAddNewBrand = () => {

        if (newBrand !== undefined && newBrand !== '') {
            dispatch(addBrand(newBrand))
            onBrandChange(newBrand)
            setShowNewBrandInput(false)
            setNewBrand('')
        }
    }

    return (
        <>
            <div className="mb-3">
                <lable className="form-label">Brand</lable>
                <select
                    className="form-control"
                    required
                    value={selectedBrand}
                    onChange={(e) => {
                        if (e.target.value === 'New') {
                            setShowNewBrandInput(true)
                        } else {
                            onBrandChange(e.target.value)
                        }
                    }}

                >
                    <option value="">All Brands</option>
                    <option value="New">Add New Brand</option>
                    {brands.map((brand, index) => (
                        <option key={index} value={brand}>
                            {brand}
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
                            onChange={(e) => setNewBrand(e.target.value)}
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