import {createAsyncThunk, createSlice} from '@reduxjs/toolkit'; 
import {api} from '../../components/services/api';

export const getAllCategories = createAsyncThunk(
    'category/getAllCategories',
    async() => {
        const response = await api.get('/categories/all');
    
        return response.data.data;
    }
);


const initialState = {
    categories: [],
    errorMessage: null,
    isLoading: false,
}

const categorySlice = createSlice({
    name: 'category',
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(getAllCategories.fulfilled, (state, action) => {
                state.categories = action.payload;
                state.errorMessage = null;
                state.isLoading = false;
            })
            .addCase(getAllCategories.rejected, (state, action) => {
                state.categories = [];
                state.errorMessage = action.error.message;
                state.isLoading = false;
            })
            .addCase(getAllCategories.pending, (state) => {
                state.categories = [];
                state.errorMessage = null;
                state.isLoading = true;
            })

    }, 
});

export default categorySlice.reducer;