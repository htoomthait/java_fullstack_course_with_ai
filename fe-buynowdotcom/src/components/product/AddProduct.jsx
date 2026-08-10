import React, { useState } from 'react'
import { addNewProduct } from '../../store/features/productSlice'
import { useDispatch } from 'react-redux';
import { toast, ToastContainer } from 'react-toastify'
import BrandSelector from '../common/BrandSelector';
import CategorySelector from '../common/CategorySelector';

const productInitialData = {
    name: "",
    description: "",
    price: 0,
    quantity: 0,
    category: "",
    brand: ""
};

const AddProduct = () => {
    const dispatch = useDispatch();
    const [showNewBrandInput, setShowNewBrandInput] = useState(false);
    const [showNewCategoryInput, setShowNewCategoryInput] = useState(false);
    const [newBrand, setNewBrand] = useState("");
    const [newCategory, setNewCategory] = useState("");
    const [product, setProduct] = useState(productInitialData)


    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setProduct((prevProduct) => ({
            ...prevProduct,
            [name]: value
        }));
    }

    const handleCategoryChange = (category) => {
        setProduct((prevProduct) => ({
            ...prevProduct,
            category: category
        }));

        if (category === "New") {
            setShowNewCategoryInput(true);
        } else {
            setShowNewCategoryInput(false);
        }
    }

    const handleBrandChange = (brand) => {
        setProduct((prevProduct) => ({
            ...prevProduct,
            brand: brand
        }));

        if (brand === "New") {
            setShowNewBrandInput(true);
        } else {
            setShowNewBrandInput(false);
        }
    }


    const handleAddNewProduct = async (e) => {
        e.preventDefault();

        try {
            const result = await dispatch(addNewProduct(productData)).unwrap();
            console.log('Product added successfully:', result);
            toast.success('Product added successfully!');
        } catch (error) {
            console.error('Error adding product:', error);
            toast.error('Error adding product.');
        }
    }

    const resetForm = () => {
        setProduct(productInitialData);
        setShowNewBrandInput(false);
        setShowNewCategoryInput(false);
    }


    return (
        <>
            <section className="mt-5 mb-5 container">
                <ToastContainer />
                <div className="d=flex justify-content-center">
                    <div className="col-md-6 col-xs-12 offset-md-3">
                        <h4>Add New Product</h4>
                        <div>
                            <form onSubmit={handleAddNewProduct}>
                                <div className="mb-3">
                                    <label htmlFor='name' className="form-label">Name:</label>
                                    <input
                                        className='form-control'
                                        type="text"
                                        name="name"
                                        value={product.name}
                                        onChange={handleInputChange}
                                        required
                                    />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor='price' className="form-label">Price:</label>
                                    <input
                                        className='form-control'
                                        type="number"
                                        name="price"
                                        value={product.price}
                                        onChange={handleInputChange}
                                        required
                                    />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor='quantity' className="form-label">Quantity:</label>
                                    <input
                                        className='form-control'
                                        type="number"
                                        name="quantity"
                                        value={product.quantity}
                                        onChange={handleInputChange}
                                        required
                                    />
                                </div>
                                <div className="mb-3">
                                    <BrandSelector
                                        selectedBrand={product.brand}
                                        onBrandChange={handleBrandChange}
                                        newBrand={newBrand}
                                        showNewBrandInput={showNewBrandInput}
                                        setNewBrand={setNewBrand}
                                        setShowNewBrandInput={setShowNewBrandInput}
                                    />
                                </div>

                                <div className="mb-3">
                                    <CategorySelector
                                        selectedCategory={product.category}
                                        onCategoryChange={handleCategoryChange}
                                        newCategory={newCategory}
                                        showNewCategoryInput={showNewCategoryInput}
                                        setNewCategory={setNewCategory}
                                        setShowNewCategoryInput={setShowNewCategoryInput}
                                    />
                                </div>


                                <div className="mb-3">
                                    <label htmlFor='description' className="form-label">Description:</label>
                                    <textarea
                                        rows={3}
                                        placeholder="Enter product description"
                                        className='form-control'
                                        name="description"
                                        value={product.description}
                                        onChange={handleInputChange}
                                        required
                                    />

                                </div>
                                <button type="submit" className="btn btn-sm btn-secondary">
                                    Save Product
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </section>
        </>
    )
}

export default AddProduct