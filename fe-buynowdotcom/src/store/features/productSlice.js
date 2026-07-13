import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'; 
import {api} from '../../components/services/api';

export const getAllProducts = createAsyncThunk(
    'product/getAllProducts',
    async() => {
        const response = await api.get('/products/all');
    
        return response.data.data;
    }
);

export const getDistinctProductsByName = createAsyncThunk(
  "product/getDistinctProductsByName",
  async () => {
    const response = await api.get("/products/distinct/products");
    return response.data.data;
  }
);

const initialState = {
    products: [],
    distinctProducts: [],
    errorMessage: null,
    isLoading: false,
}

const productSlice = createSlice({
    name: 'product',
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(getAllProducts.fulfilled, (state, action) => {
                state.products = action.payload;
                state.errorMessage = null;
                state.isLoading = false;
            })
            .addCase(getAllProducts.rejected, (state, action) => {
                state.products = [];
                state.errorMessage = action.error.message;
                state.isLoading = false;
            })
            .addCase(getAllProducts.pending, (state) => {
                state.products = [];
                state.errorMessage = null;
                state.isLoading = true;
            })

    }, 
});

export default productSlice.reducer;