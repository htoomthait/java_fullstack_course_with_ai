import {configureStore} from '@reduxjs/toolkit';
import SearchReducer from './features/searchSlice';
import CategoryReducer from './features/categorySlice';
import productReducer from './features/productSlice';

export const store = configureStore({
    reducer:{
        search: SearchReducer,
        category: CategoryReducer,
        product: productReducer,
    }
});