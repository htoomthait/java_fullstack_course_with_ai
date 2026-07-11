import React, { useState, useEffect } from 'react'

const ProductImage = ({
    productId,
}) => {
    const [productImage, setProductImage] = useState(null);



    useEffect(() => {
        const fetchProductImage = async () => {

            try {
                const response = await fetch(
                    `http://localhost:8080/api/v1/images/download/${productId}`
                );
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



        if (productId) {
            fetchProductImage();
        }

    }, [productId]);

    if (!productImage) return null;

    return (
        <div>
            <img src={productImage} alt='Product Image' />
        </div>
    )
}

export default ProductImage