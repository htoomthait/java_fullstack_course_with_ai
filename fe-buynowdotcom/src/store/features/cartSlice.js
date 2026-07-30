import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'; 
import {api} from '../../components/services/api';

const token = localStorage.getItem("token") || "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huam9obkBnbWFpbC5jb20iLCJyb2xlcyI6W10sImlhdCI6MTc4NTQzMzcwMywiZXhwIjoxNzg1NDM3MzAzfQ.JoevFK8p53-DqJ95LgQu9_uKbru54WTCHzfEw3Ud_DBP97aPSk-Tl_2rnDnn26vUBd-i10F6YITB_3LM6uK58Q";


export const addToCart = createAsyncThunk(
  "cart/addToCart",
  async ({ productId, quantity }, { rejectWithValue }) => {
    try {
      const response = await api.post(
        "/cart-items/item/add",
        null,
        {
          params: { productId, quantity },
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      return response.data;
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message || "Failed to add item to cart."
      );
    }
  }
);

export const getUserCart =  createAsyncThunk(
    'cart/getCartItemByUserId', async(userId) => {
        const response = await api.get(`/carts/user/${userId}/cart`);

        return response.data.data;
    }
)

export const updateQuantity = createAsyncThunk(
    'cart/updateQuantity', async ({cartId, itemId, newQuantity}) =>{
        const response = await api.patch(`cart-items/cart/${cartId}/item/${itemId}/update?quantity=${newQuantity}`);

        return {itemId, newQuantity};
    }    

)

export const removeItemFromCart = createAsyncThunk(
  "cart/removeItemFromCart",
  async ({ cartId, itemId }, { rejectWithValue }) => {
    try {
      await api.delete(`/cart-items/cart/${cartId}/item/${itemId}/remove`);
      return itemId;
    } catch (error) {
      return rejectWithValue(
        error.response?.data?.message || "Failed to remove cart item."
      );
    }
  }
);

const initialState = {
    items: [],
    totalAmount:0,
    cartId: null,
    errorMessage: null,
    successMessage: null,
    isLoading:true,

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
                state.items = [];
                state.totalAmount=0,
                state.isLoading = false;
            })
            .addCase(updateQuantity.fulfilled, (state, action) => {
                const { itemId, newQuantity } = action.payload;

                const item = state.items.find((item) => item.product.id === itemId);
                if (item) {
                    item.quantity = newQuantity;
                    item.totalPrice = item.product.price * newQuantity;
                }
                state.totalAmount = state.items.reduce(
                (   total, item) => total + item.totalPrice,
                    0
                );
                state.isLoading = false;
            })
            .addCase(updateQuantity.pending, (state, action) => {
                state.isLoading = true;
            })
            .addCase(removeItemFromCart.fulfilled, (state, action) => {
                const itemId = action.payload;
                state.items = state.items.filter((item) => item.product.id !== itemId);
                state.totalAmount = state.items.reduce(
                    (total, item) => total + item.totalPrice, 0
                );
                state.isLoading = false;
            })
            .addCase(removeItemFromCart.pending, (state) => {
                state.isLoading = true;
                state.error = null;
            })
            .addCase(removeItemFromCart.rejected, (state, action) => {
                state.isLoading = false;
                state.error = action.payload || action.error.message;
            });
    }, 
});


export default cartSlice.reducer;