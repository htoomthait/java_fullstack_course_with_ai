import React, { useState } from 'react'

const ImageUploader = ({ productId }) => {
    const [images, setImages] = useState([]);


    const handleImageChange = (e) => {
        e.preventDefault();
        const files = Array.from(e.target.files);

        const newImages = files.map((file) => {

        })

        //setImages(files);
    }
    return (
        <div>ImageUploader</div>
    )
}

export default ImageUploader