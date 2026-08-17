import React, { useState } from 'react'
import { nanoid } from 'nanoid';
import { uploadImages } from '../../store/features/imageSlice';
import { useDispatch } from 'react-redux';
import { toast, ToastContainer } from 'react-toastify';

const ImageUploader = ({ productId }) => {
    const [images, setImages] = useState([]);
    const dispatch = useDispatch();


    const handleImageChange = (e) => {
        e.preventDefault();
        const files = Array.from(e.target.files);

        const newImages = files.map((file) => ({
            id: nanoid(),
            name: file.name,
            file


        })
        );

        setImages((prevImages) => [...prevImages, ...newImages]);
    }

    const handleImageUpload = async (e) => {
        e.prevImagesentDefault();

        if (!productId) {
            return;
        }

        if (Array.isArray(images) && images.length > 0) {
            try {
                const result = await dispatch(
                    uploadImages({ productId, files: images.map((image) => image.file) })
                ).unwrap();

                clearFileInput();
                toast.success('Images uploaded successfully');

            } catch (error) {
                toast.error('Failed to upload images');
            }
        }

    }



    return (
        <div>ImageUploader</div>
    )
}

export default ImageUploader