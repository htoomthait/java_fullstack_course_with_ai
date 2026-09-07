import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { Link, useParams } from 'react-router-dom';
import { getProductById, updateProduct } from "../../store/features/productSlice";
import LoadSpinner from '../common/LoadSpinner';
import { toast, ToastContainer } from 'react-toastify';
import CategorySelector from '../common/CategorySelector';
import BrandSelector from '../common/BrandSelector';
import ProductImage from '../utils/ProductImage';


const ProductUpdate = () => {
    const dispatch = useDispatch();
    const [showNewBrandInput, setShowNewBrandInput] = useState(false);
    const [showNewCategoryInput, setShowNewCategoryInput] = useState(false);
    const [newBrand, setNewBrand] = useState("");
    const [newCategory, setNewCategory] = useState("");
    const [activeStep, setActiveStep] = useState(0);
    const steps = ["Add Product", "Upload Product Image (s)"];
    const { productId } = useParams();
    const isLoading = useSelector((state) => state.product.isLoadingUpdateProduct)


    const [updatedProduct, setUpdatedProduct] = useState({
        name: "",
        brand: "",
        price: 0,
        quantity: 0,
        description: "",
        category: "",
        images: []
    });



    useEffect(() => {


        const fetchProduct = async () => {
            try {
                const result = await dispatch(getProductById(productId)).unwrap();
                setUpdatedProduct((prevProduct) => ({
                    ...prevProduct,
                    ...result,
                    quantity: result.inventory,
                    category: result.category?.name || "",
                }));
            } catch (error) {
                console.log(`Fail to fetch product with ID ${productId}`, error);
                toast.error(error?.message || `Fail to fetch product with ID ${productId}`);
            }
        }
        fetchProduct();

    }, [dispatch, productId])


    const handleInputChange = (e) => {
        const { name, value, type } = e.target;
        setUpdatedProduct((prevProduct) => ({
            ...prevProduct,
            [name]: type === "number" ? Number(value) : value
        }));
    }

    const handleCategoryChange = (category) => {
        setUpdatedProduct((prevProduct) => ({
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
        setUpdatedProduct((prevProduct) => ({
            ...prevProduct,
            brand: brand
        }));

        if (brand === "New") {
            setShowNewBrandInput(true);
        } else {
            setShowNewBrandInput(false);
        }
    }

    const handleUpdateProduct = async (e) => {
        e.preventDefault();

        console.log("Updating product with ID:", productId, "Updated Product:", updatedProduct);

        try {
            const result = await dispatch(
                updateProduct({ productId, updatedProduct })
            ).unwrap();
            toast.success(result.message);

        } catch (error) {
            console.log(`Fail to update product with ID ${productId}`, error);
            toast.error(error?.message || `Fail to update product with ID ${productId}`)
        }
    }





    if (isLoading) {
        return <>
            <LoadSpinner />
        </>
    }


    return (
        <>

            <div className="container mt-5 mb-5">
                <ToastContainer />
                <div className="row d-flex justify-content-center">
                    <div className="col-md-6 me-4">
                        <h4> Update Product </h4>
                        <form onSubmit={handleUpdateProduct}>
                            <div className="mb-3">
                                <label className="form-label">Name: </label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="name"
                                    name="name"
                                    value={updatedProduct.name}
                                    onChange={handleInputChange}
                                    required
                                />
                            </div>
                            <div className="mb-3">
                                <label className="form-label">Price: </label>
                                <input
                                    type="number"
                                    className="form-control"
                                    id="price"
                                    name="price"
                                    value={updatedProduct.price}
                                    onChange={handleInputChange}
                                    required
                                />
                            </div>
                            <div className="mb-3">
                                <label className="form-label">Quantity: </label>
                                <input
                                    type="number"
                                    className="form-control"
                                    id="quantity"
                                    name="quantity"
                                    value={updatedProduct.quantity}
                                    onChange={handleInputChange}
                                    required
                                />
                            </div>
                            <div className="mb-3">
                                <BrandSelector
                                    selectedBrand={updatedProduct.brand}
                                    onBrandChange={handleBrandChange}
                                    newBrand={newBrand}
                                    showNewBrandInput={showNewBrandInput}
                                    setNewBrand={setNewBrand}
                                    setShowNewBrandInput={setShowNewBrandInput}
                                />
                            </div>
                            <div className="mb-3">
                                <CategorySelector
                                    selectedCategory={updatedProduct.category}
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
                                    value={updatedProduct.description}
                                    onChange={handleInputChange}
                                    required
                                />

                            </div>
                            <button type="submit" className="btn btn-sm btn-secondary">
                                Save Product Update
                            </button>

                        </form>
                    </div>
                    <div className="col-md-3">
                        <table className="table table-bordered text-center">
                            <tbody>
                                {updatedProduct.images.map((image, index) => (

                                    < tr key={index} >

                                        <td className="update-image-container">
                                            <ProductImage imageId={image.id} />
                                            <div className="d-flex gap-4 mb-2 mt-2">
                                                <Link to={"#"}> edit</Link>
                                                <Link to={"#"}> remove</Link>
                                            </div>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                            <Link to={"#"}> Add Image</Link>
                        </table>
                    </div>
                </div>
            </div >
        </>

    )
}

export default ProductUpdate