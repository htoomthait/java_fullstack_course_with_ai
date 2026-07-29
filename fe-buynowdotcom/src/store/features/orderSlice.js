import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'; 
import {api} from '../../components/services/api';



export const placeOrder = createAsyncThunk(
    "orders/placeOrder", async (userId, {rejectWithValue}) => {

        try {
            const response = await api.post(`orders/user/order?userId=${userId}`);

            console.log("The reponse from the order slice: ",response.data)
            console.log("The reponse from the order slice: ",response.data.data)
            return response.data;
        } catch (error) {
           return rejectWithValue(
                error.response?.data?.message || "Failed to place order."
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
    }
});

export default orderSlice.reducer;