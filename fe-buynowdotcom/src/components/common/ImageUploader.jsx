import React, { useState, useRef } from 'react'
import { nanoid } from 'nanoid';
import { uploadImages } from '../../store/features/imageSlice';
import { useDispatch } from 'react-redux';
import { toast, ToastContainer } from 'react-toastify';
import { BsDash, BsPlus } from 'react-icons/bs';
import { Link } from 'react-router-dom';

const ImageUploader = ({ productId }) => {
    const [images, setImages] = useState([]);
    const fileInputRefs = useRef([]);
    const [imageInput, setImageInput] = useState([{ id: nanoid() }]);
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

    const handleAddImageInput = () => {
        setImageInput((prevInputs) => [...prevInputs, { id: nanoid() }]);
        console.log("The added Input : ", imageInput);
    }

    const handleRemoveImageInput = (id) => {
        setImageInput((prevInputs) => prevInputs.filter((input) => input.id !== id));
        console.log("The removed Input : ", imageInput);
    }

    const handleImageUpload = async (e) => {
        e.preventDefault();

        console.log("You 've reached to image upload function");

        if (!productId) {
            return;
        }

        if (Array.isArray(images) && images.length > 0) {
            try {
                console.log("productId:", productId);
                console.log(
                    "files:",
                    images.map((image) => image.file)
                );

                images.forEach((image) => {
                    console.log(
                        image.file,
                        image.file instanceof File
                    );
                });

                const result = await dispatch(
                    uploadImages({ productId, files: images.map((image) => image.file) })
                ).unwrap();

                clearFileInput();
                toast.success('Images uploaded successfully');

            } catch (error) {
                console.log(error);
                toast.error('Failed to upload images');
            }
        }

    }

    const clearFileInput = () => {
        fileInputRefs.current.forEach((input) => {
            if (input) input.value = null;
        });
        setImages([]);
        setImageInput([{ id: nanoid() }]);
    }



    return (
        <>
            <form onSubmit={handleImageUpload}>
                <div className="mt-4">

                    <h4> Upload Product Image (s)</h4>
                    <Link to={"#"} onClick={handleAddImageInput} className="btn btn-sm btn-outline-primary mb-2">
                        <BsPlus className='icon' /> Add More Images
                    </Link>

                    <div className="mb-2 mt-2">
                        {imageInput.map((input, index) => (
                            <div key={input.id} className="d-flex align-items-center mb-2 input-group">
                                <input
                                    type="file"
                                    accept="image/*"
                                    onChange={(e) => handleImageChange(e)}
                                    className="form-control me-2"
                                    ref={(el) => (fileInputRefs.current[index] = el)}
                                />
                                <button
                                    type="button"
                                    onClick={() => handleRemoveImageInput(input.id)}
                                    className="btn btn-danger btn-sm"
                                >
                                    <BsDash className='icon' />
                                </button>

                            </div>
                        ))}
                    </div>

                    {imageInput.length > 0 && (

                        <button
                            type="submit"
                            className="btn btn-primary btn-sm"
                        >
                            Upload Images
                        </button>

                    )}
                </div>
            </form>
        </>
    )
}

export default ImageUploader