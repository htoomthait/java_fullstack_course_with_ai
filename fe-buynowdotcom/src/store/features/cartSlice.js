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
        return response.data;
    }

);

export const getUserCart =  createAsyncThunk(
    'cart/getCartItemByUserId', async(userId) => {
        const response = await api.get(`/carts/user/${userId}/cart`);

        return response.data.data;
    }
)

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
                state.isLoading = false;
                state.successMessage = action.payload.message || "Item added to cart successfully";
            })
            .addCase(addToCart.rejected, (state, action) => {
                state.errorMessage = action.error.message;
                state.isLoading = false;
            })
            .addCase(addToCart.pending, (state) => {
                state.errorMessage = null;
                state.successMessage = null;
                state.isLoading = true;
            })
            .addCase(getUserCart.fulfilled, (state, action) => {
                state.items = action.payload.cartItems;
                state.cartId = action.payload.id;
                state.totalAmount = action.payload.total;
                state.errorMessage = null;
                state.isLoading = false;
            })
            .addCase(getUserCart.rejected, (state, action) => {
                state.errorMessage = action.error.message;
            })
    }, 
});


export default cartSlice.reducer;