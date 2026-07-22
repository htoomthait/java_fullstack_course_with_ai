import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'; 
import {api} from '../../components/services/api';


export const addToCart = createAsyncThunk(
    'cart/addToCart', async({productId, quantity}) => {
        const response = await api.post("/cart-items/item/add", {productId, quantity});
        return response.data.data;
    }

);

const initialState = {
    items: [],
    totalAmount:0,
    cartId: null,
    errorMessage: null,
    successMessage: null,

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
                state.successMessage = "Item added to cart successfully";
            })
            .addCase(addToCart.rejected, (state, action) => {
                state.errorMessage = action.error.message;
                state.successMessage = null;
            })
            .addCase(addToCart.pending, (state) => {
                state.errorMessage = null;
                state.successMessage = null;
            })
    }, 
});


export default cartSlice.reducer;