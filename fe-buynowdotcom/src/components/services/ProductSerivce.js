import {api} from "./api"

export const getDistinctProductsByName = async () => {

    try {
        const response = await api.get("products/distinct/products");
        return response.data;
    } catch (error) {
        console.error("Error fetching distinct products:", error);
        throw error;
    }
}