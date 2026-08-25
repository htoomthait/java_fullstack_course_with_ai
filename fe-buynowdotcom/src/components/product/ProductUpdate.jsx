import React, { useEffect, useState } from 'react'
import { useDispatch } from 'react-redux';
import { useParams } from 'react-router-dom';
import { getProductById, updateProduct } from "../../store/features/productSlice";
import LoadSpinner from '../common/LoadSpinner';
import { toast, ToastContainer } from 'react-toastify';


const ProductUpdate = () => {
    const dispatch = useDispatch();
    const [showNewBrandInput, setShowNewBrandInput] = useState(false);
    const [showNewCategoryInput, setShowNewCategoryInput] = useState(false);
    const [newBrand, setNewBrand] = useState("");
    const [newCategory, setNewCategory] = useState("");
    const [product, setProduct] = useState(productInitialData)
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
        dispatch(getProductById(productId));
    }, [dispatch, productId])


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

    const handleUpdateProduct = async (e) => {
        e.preventDefault();

        try {
            const result = await dispatch(
                updateProduct({ productId, updatedProduct })
            ).unwrap();
            toast.success(result.message);

        } catch (error) {
            console.log(`Fail to update product with ID ${productId}`, error);
            toast.errror(error?.message || `Fail to update product with ID ${productId}`)
        }
    }





    if (isloading) {
        return <>
            <LoadSpinner />
        </>
    }


    return (
        <div>ProductUpdate</div>
    )
}

export default ProductUpdate