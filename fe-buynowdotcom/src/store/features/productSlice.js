import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'; 
import {api} from '../../components/services/api';

export const getAllProducts = createAsyncThunk(
    'product/getAllProducts',
    async() => {
        const response = await api.get('/products/all');
    
        return response.data.data;
    }
);

export const getAllBrands = createAsyncThunk(
    "product/getAllBrands",
    async () => {
        const response = await api.get("/products/distinct/brands")
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
    brands:[],
    selectedBrands: [],
    distinctProducts: [],
    errorMessage: null,
    isLoadingGetAllProducts: false,
    isLoadingGetAllBrands: false,
    isLoadingGetAllDistinctProductByName: false
}

const productSlice = createSlice({
    name: 'product',
    initialState,
    reducers: {
        filterByBrands: (state, action) => {
            const {brand, isChecked} = action.payload;
            if(isChecked){
                state.selectedBrands.push(brand)
            }else{
                state.selectedBrands = state.selectedBrands.filter((b) => b != brand)
            }

        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(getAllProducts.fulfilled, (state, action) => {
                state.products = action.payload;
                state.errorMessage = null;
                state.isLoadingGetAllProducts = false;
            })
            .addCase(getAllProducts.rejected, (state, action) => {
                state.products = [];
                state.errorMessage = action.error.message;
                state.isLoadingGetAllProducts = false;
            })
            .addCase(getAllProducts.pending, (state) => {
                state.products = [];
                state.errorMessage = null;
                state.isLoadingGetAllProducts = true;
            })
            .addCase(getAllBrands.fulfilled, (state, action) => {
                state.brands = action.payload;
                state.errorMessage = null;
                state.isLoadingGetAllBrands = false;
            })
            .addCase(getAllBrands.rejected, (state, action) => {
                state.brands = [];
                state.errorMessage = action.error.message;
                state.isLoadingGetAllBrands = false;
            })
            .addCase(getAllBrands.pending, (state) => {
                state.brands = [];
                state.errorMessage = null;
                state.isLoadingGetAllBrands = true;
            })

    }, 
});

export const { filterByBrands } = productSlice.actions;
export default productSlice.reducer;