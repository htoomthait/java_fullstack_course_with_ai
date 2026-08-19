import React, { useState } from 'react'
import { addNewProduct } from '../../store/features/productSlice'
import { useDispatch } from 'react-redux';
import { toast, ToastContainer } from 'react-toastify'
import BrandSelector from '../common/BrandSelector';
import CategorySelector from '../common/CategorySelector';
import { Stepper, Step, StepLabel } from '@mui/material';
import ImageUploader from '../common/ImageUploader';

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
    const [activeStep, setActiveStep] = useState(0);
    const steps = ["Add Product", "Upload Product Image (s)"];
    const [productId, setProductId] = useState(null);



    const handleInputChange = (e) => {
        const { name, value, type } = e.target;
        setProduct((prevProduct) => ({
            ...prevProduct,
            [name]: type === "number" ? Number(value) : value
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
            // console.log('Adding product:', product);
            const result = await dispatch(addNewProduct(product)).unwrap();
            console.log('Product added successfully:', result);
            setProductId(result.data.id);
            toast.success(result.message || 'Product added successfully');
            resetForm();
            setActiveStep(1);
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
                        <Stepper activeStep={activeStep} className="mb-4">
                            {
                                steps.map((label, index) => (
                                    <Step key={index}>
                                        <StepLabel>{label}</StepLabel>
                                    </Step>
                                ))
                            }

                        </Stepper>



                        <div>
                            {activeStep === 0 && (
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
                            )}
                            {
                                activeStep === 1 && (
                                    <div className="container">
                                        <ImageUploader productId={productId} />
                                    </div>

                                )
                            }

                            <div className="container">
                                <ImageUploader productId={40} />
                            </div>



                        </div>
                    </div>
                </div>
            </section>
        </>
    )
}

export default AddProduct