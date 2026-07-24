import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'; 
import {api} from '../../components/services/api';

const token = localStorage.getItem("token") || "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huam9obkBnbWFpbC5jb20iLCJyb2xlcyI6W10sImlhdCI6MTc4NDg4NDU1MSwiZXhwIjoxNzg0ODg4MTUxfQ.846ZPsnYmG3ROAyHcXWIXxkcpArRmmcCDNt9Z67aeQM51AzucOSyTsU29xsiA7Bv_CeaOdnfm0rfj8nWU_1JsA";


export const addToCart = createAsyncThunk(
    'cart/addToCart', async({productId, quantity}) => {
        const response = await api.post("/cart-items/item/add", null, {
            params: {productId, quantity} ,
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        console.log("The response from addToCart API 1:", response.data);
        console.log("The response from addToCart API 2:", response.data.data);
        return response.data;
    }

);

const initialState = {
    items: [],
    totalAmount:0,
    cartId: null,
    errorMessage: null,
    successMessage: null,
    isLoading:false,

}

const cartSlice = createSlice({
    name: 'cart',
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(addToCart.fulfilled, (state, action) => {
                state.items = action.payload.items;
                state.cartId = action.payload.cartId;
                state.totalAmount = action.payload.totalAmount;
                state.errorMessage = null;
                state.isLoading = false;
                state.successMessage = action.payload.message || "Item added to cart successfully";
            })
            .addCase(addToCart.rejected, (state, action) => {
                state.errorMessage = action.error.message;
                state.successMessage = null;
            })
            .addCase(addToCart.pending, (state) => {
                state.errorMessage = null;
                state.successMessage = null;
                state.isLoading = true;
            })
    }, 
});


export default cartSlice.reducer;