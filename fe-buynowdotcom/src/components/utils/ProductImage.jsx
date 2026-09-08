import React, { useState, useEffect } from 'react'

const ProductImage = ({
    imageId,
}) => {
    const [productImage, setProductImage] = useState(null);



    useEffect(() => {
        const fetchProductImage = async () => {

            try {
                const url = `http://localhost:8080/api/v1/images/download/${imageId}`;
                const response = await fetch(url, { cache: 'no-store' });
                const blob = await response.blob();
                const reader = new FileReader();
                reader.onloadend = () => {
                    setProductImage(reader.result);
                };
                reader.readAsDataURL(blob);
            } catch (error) {
                console.error('Error fetching product image:', error);
            }
        }



        if (imageId) {
            fetchProductImage();
        }

    }, [imageId]);

    if (!productImage) return null;

    return (
        <div>
            <img src={productImage} alt='Product Image' />
        </div>
    )
}

export default ProductImage