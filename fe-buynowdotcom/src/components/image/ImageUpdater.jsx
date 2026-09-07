import React, { useRef } from 'react'
import { useDispatch } from 'react-redux';
import { updateProductImage, uploadImages } from '../../store/features/imageSlice';

const ImageUpdater = ({
    show,
    handleClose,
    selectedImageId,
    productId,
    selectedImage
}) => {
    const fileInputRef = useRef(null);
    const dispatch = useDispatch();
    const [selectedFile, selectedFile] = useState(null);
    const [imagePreview, setImagePreview] = useState(null);


    useEffect(() => {
        if (selectedImage) {
            setImagePreview(selectedImage.imageUrl)
        } else {
            setImagePreview(null);
        }


    }, [selectedImage]);

    const handleFileChange = (event) => {
        const file = event.target.files[0];
        setSelectedFile(file);

        if (file) {
            const reader = new FileReader();
            reader.onload = (e) => {
                setImagePreview(e.target.result);
            }
            reader.readAsDataURL(file);
        } else {
            setImagePreview(null);
        }
    }

    const handleImageAction = async () => {
        if (!selectedFile) {
            toast.warn("Please select an image.")
        }

        try {
            let result;
            if (selectedImageId) {
                result = await dispatch(
                    updateProductImage({ productId, imageId, file: selectedFile })
                ).unwrap();


            } else {
                result = await dispatch(
                    uploadImages({ productId, files: [selectedFile] })
                ).unwrap();
            }

            toast.success(result.message || "Image updated successfully");
            handleClose();

        } catch (error) {
            toast.error(error?.message || "Failed to update image");
        }
    }

    const handleClose = () => {
        setSelectedFile(null);
        setImagePreview(null);

    }



    return (
        <>

        </>
    )
}

export default ImageUpdater