import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'; 
import {api} from '../../components/services/api';



export const placeOrder = createAsyncThunk(
    "orders/placeOrder", async (userId, {rejectWithValue}) => {

        try {
            const response = await api.post(`orders/user/order?userId=${userId}`);

            return response.data;
        } catch (error) {
           return rejectWithValue(
                error.response?.data?.message || "Failed to place order."
            );
        }
    }
)

export const fetchUserOrder = createAsyncThunk(
    "order/fetchUserOrcer", async (userId, {rejectWithValue}) => {

        try {
            const response = await api.get(`orders/user/${userId}/order`);
            console.log("From order fetch of order slice 1: ", response.data);
            console.log("From order fetch of order slice 2: ", response.data.data);

            return response.data;
        } catch (error) {
            return rejectWithValue(
                error.response?.data?.message || "Fail to fetch orders of given user"
            );
        }
    } 
)

const initialState = {
    orders: [],
    isLoading: false,
    errorMessage: null,
    successMessage: null,
};

const orderSlice = createSlice({
    name: "order",
    initialState,
    reducers: {},
    extraReducers: (builder) =>{
        builder
            .addCase(placeOrder.fulfilled, (state, action) => {
                state.isLoading = false;
                state.successMessage = action.payload.message;
                state.orders.push(action.payload.data);
            })
            .addCase(placeOrder.rejected, (state, action) => {
                state.isLoading = false
                // state.errorMessage = action.payload || action.error.message;
            })
            .addCase(placeOrder.pending, (state, action) => {
                state.isLoading = true;
                state.successMessage = null;
                state.errorMessage = null;

            })
            .addCase(fetchUserOrder.fulfilled, (state, action) => { 
                state.orders = action.payload.data;
                state.successMessage = action.payload.message;
                state.errorMessage = null;
                state.isLoading = false;
            })
            .addCase(fetchUserOrder.rejected, (state, action) => { 
                 state.isLoading = false
            })
            .addCase(fetchUserOrder.pending, (state, action) => { 
                state.isLoading = true;
                state.successMessage = null;
                state.errorMessage = null;
            })
    }
});

export default orderSlice.reducer;