import React from 'react'
import { addNewProduct } from '../../store/features/productSlice'
import { useDispatch } from 'react-redux';
import { toast, ToastContainer } from 'react-toastify'

const AddProduct = () => {
    const dispatch = useDispatch();
    const [showNewBrandInput, setShowNewBrandInput] = useState(false);
    const [showNewCategoryInput, setShowNewCategoryInput] = useState(false);
    const [newBrand, setNewBrand] = useState("");
    const [newCategory, setNewCategory] = useState("");
    const [product, setProduct] = useState({
        name: "",
        description: "",
        price: 0,
        quantity: 0,
        category: "",
        brand: ""
    })


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


    return (
        <div>AddProduct</div>
    )
}

export default AddProduct