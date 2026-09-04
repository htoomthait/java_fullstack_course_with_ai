import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'; 
import {api} from '../../components/services/api';

export const getAllProducts = createAsyncThunk(
    'product/getAllProducts',
    async() => {
        const response = await api.get('/products/all');
    
        return response.data.data;
    }
);

export const addNewProduct = createAsyncThunk(
    'product/addNewProduct',
    async (productData, { rejectWithValue }) => {
        try {
            const response = await api.post(
                '/products/add',
                productData
            );

            return response.data;

        } catch (error) {
            // Server responded with an error
            if (error.response) {
                return rejectWithValue(error.response.data);
            }

            // Request was sent but no response received
            if (error.request) {
                return rejectWithValue({
                    message: 'No response from server. Please try again.'
                });
            }

            // Other JavaScript/Axios errors
            return rejectWithValue({
                message: error.message || 'Something went wrong.'
            });
        }
    }
);

export const updateProduct = createAsyncThunk(
    'product/updateNewProduct',
    async ({productId, updatedProduct}, { rejectWithValue }) => {
        try {
            const response = await api.put(`/products/${productId}`, updatedProduct)
            return response.data

        } catch (error) {
            // Server responded with an error
            if (error.response) {
                return rejectWithValue(error.response.data);
            }

            // Request was sent but no response received
            if (error.request) {
                return rejectWithValue({
                    message: 'No response from server. Please try again.'
                });
            }

            // Other JavaScript/Axios errors
            return rejectWithValue({
                message: error.message || 'Something went wrong.'
            });
        }
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

export const getProductById = createAsyncThunk(
    "product/getProductById",
    async (productId) => {
        const response = await api.get(`/products/${productId}`)
        return response.data.data;
    }
);

export const getProductsByCategory = createAsyncThunk(
    "product/getProductsByCategory",
    async (categoryId) => {
        const response = await api.get(`/products/category/${categoryId}`)
        return response.data.data;
    }
);

const initialState = {
    products: [],
    brands:[],
    selectedBrands: [],
    distinctProducts: [],
    product: null,
    quantity: 1,
    productsByCategory:[],
    errorMessage: null,
    isLoadingGetAllProducts: false,
    isLoadingGetAllBrands: false,
    isLoadingGetAllDistinctProductByName: false,
    isLoadingAddNewProduct: false,
    isLoadingUpdateProduct: false
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

        },
        decreaseQuantity: (state, action) => {
            if (state.quantity > 1) {
                state.quantity--;
            }
        },
        increaseQuantity: (state, action) => {
            state.quantity++;
        },
        setQuantity: (state, action) => {
            state.quantity = action.payload;
        },
        addBrand: (state, action) => {
            state.brands.push(action.payload);
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

            .addCase(getDistinctProductsByName.fulfilled, (state, action) => {
                state.distinctProducts = action.payload;
                state.errorMessage = null;
                state.isLoadingGetAllDistinctProductByName = false;
            })
            .addCase(getDistinctProductsByName.rejected, (state, action) => {
                state.distinctProducts = [];
                state.errorMessage = action.error.message;
                state.isLoadingGetAllDistinctProductByName = false;
            })
            .addCase(getDistinctProductsByName.pending, (state) => {
                state.distinctProducts = [];
                state.errorMessage = null;
                state.isLoadingGetAllDistinctProductByName = true;
            })
            .addCase(getProductById.fulfilled, (state, action) => {
                state.product= action.payload;
                state.errorMessage = null;
               
            })
            .addCase(getProductById.rejected, (state, action) => {
                state.product= null;
                state.errorMessage = action.error.message;
               
            })

           .addCase(getProductsByCategory.fulfilled, (state, action) => {
                state.products = action.payload;
                state.productsByCategory = action.payload;
                state.errorMessage = null;
                state.isLoadingGetAllProducts = false;
            })
            .addCase(getProductsByCategory.rejected, (state, action) => {
                state.products = [];
                state.productsByCategory = [];
                state.errorMessage = null;
                state.isLoadingGetAllProducts = false;
            })
            .addCase(addNewProduct.fulfilled, (state, action) => {
                state.products.push(action.payload);
                state.errorMessage = null;
                state.isLoadingAddNewProduct = false;
            })
            .addCase(addNewProduct.rejected, (state, action) => {
                state.errorMessage = action.error.message;
                state.isLoadingAddNewProduct = false;
            })
            .addCase(addNewProduct.pending, (state) => {
                state.errorMessage = null;
                state.isLoadingAddNewProduct = true;
            })
            .addCase(updateProduct.fulfilled, (state, action)=> {
                state.product = action.payload.data;
                state.isLoadingUpdateProduct = false;
            })
            .addCase(updateProduct.rejected, (state, action)=> {

                state.isLoadingUpdateProduct = false;
            })
            .addCase(updateProduct.pending, (state, action)=> {
                state.errorMessage = null;
                state.isLoadingUpdateProduct = true;
            })

    }, 
});

export const { filterByBrands, decreaseQuantity, increaseQuantity, setQuantity, addBrand } = productSlice.actions;
export default productSlice.reducer;