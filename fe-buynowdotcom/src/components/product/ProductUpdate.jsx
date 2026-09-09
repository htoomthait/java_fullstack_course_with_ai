import React, { use, useCallback, useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { Link, useParams } from 'react-router-dom';
import { getProductById, updateProduct } from "../../store/features/productSlice";
import LoadSpinner from '../common/LoadSpinner';
import { toast, ToastContainer } from 'react-toastify';
import CategorySelector from '../common/CategorySelector';
import BrandSelector from '../common/BrandSelector';
import ProductImage from '../utils/ProductImage';
import ImageUpdater from '../image/ImageUpdater';
import { deleteProductImage } from '../../store/features/imageSlice';


const ProductUpdate = () => {
    const dispatch = useDispatch();
    const [showNewBrandInput, setShowNewBrandInput] = useState(false);
    const [showNewCategoryInput, setShowNewCategoryInput] = useState(false);
    const [newBrand, setNewBrand] = useState("");
    const [newCategory, setNewCategory] = useState("");
    const [activeStep, setActiveStep] = useState(0);
    const steps = ["Add Product", "Upload Product Image (s)"];
    const { productId } = useParams();
    // const isLoading = useSelector((state) => state.product.isLoadingUpdateProduct)
    const [isLoading, setIsLoading] = useState(false);

    const [showImageModal, setShowImageModal] = useState(false);
    const [selectedImageId, setSelectedImageId] = useState(null);
    const newProductImage = useSelector((state) => state.image.images);


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
        console.log("New product image state changed:", newProductImage);
    }, [newProductImage])



    useEffect(() => {
        const fetchProduct = async () => {
            try {
                setIsLoading(true);
                const result = await dispatch(getProductById(productId)).unwrap();

                setUpdatedProduct((prevProduct) => ({
                    ...prevProduct,
                    ...result,
                    quantity: result.inventory,
                    category: result.category?.name || "",
                }));
            } catch (error) {
                console.log(
                    `Fail to fetch product with ID ${productId}`,
                    error
                );

                toast.error(
                    error?.message ||
                    `Fail to fetch product with ID ${productId}`
                );
            } finally {
                setTimeout(() => {
                    setIsLoading(false);
                }, 1000);

            }
        };

        fetchProduct();

    }, [dispatch, productId, newProductImage]);


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

    const handleEditImage = (imageId) => {
        setSelectedImageId(imageId);
        setShowImageModal(true);
    }

    const handleRemoveImage = async (imageId) => {
        try {
            const result = await dispatch(deleteProductImage({ imageId })).unwrap();
            toast.success(result.message || `Image with ID ${imageId} removed successfully`);

            setUpdatedProduct((prevProduct) => ({
                ...prevProduct,
                images: prevProduct.images.filter((image) => image.id !== imageId)
            }));
        } catch (error) {
            toast.error(error?.message || `Fail to remove image with ID ${imageId}`)
        }
    }

    const handleAddImage = () => {
        setShowImageModal(true);
        setSelectedImageId(null);
    }

    const handleCloseImageModal = async () => {
        setShowImageModal(false);
        setSelectedImageId(null);



    }

    useEffect(() => {
        console.log("Updated Product state changed:", updatedProduct);
    }, [updatedProduct]);





    if (isLoading) {
        return <>

            <LoadSpinner />
        </>
    }


    return (
        <>

            <div className="container mt-5 mb-5">

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
                    <div className="d-flex flex-column col-md-3 justify-content-center">
                        <table className="table table-bordered text-center">
                            <tbody>
                                {updatedProduct.images.map((image, index) => (


                                    < tr key={image.id} >
                                        {/* {console.log(image)} */}

                                        <td className="update-image-container">
                                            <ProductImage imageId={image.id} />
                                            <div className="d-flex gap-4 mb-2 mt-2 justify-content-center">
                                                <Link className="btn btn-sm btn-outline-primary" to={"#"} onClick={() => handleEditImage(image.id)}> edit</Link>
                                                <Link className="btn btn-sm btn-outline-danger" to={"#"} onClick={() => handleRemoveImage(image.id)}> remove</Link>
                                            </div>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>

                        </table>
                        <Link className="btn btn-sm btn-outline-success" to={"#"} onClick={handleAddImage}>
                            Add Image
                        </Link>
                    </div>
                </div>

                <ImageUpdater
                    show={showImageModal}
                    handleClose={handleCloseImageModal}
                    selectedImageId={selectedImageId}
                    productId={productId}


                />
            </div >
        </>

    )
}

export default ProductUpdate