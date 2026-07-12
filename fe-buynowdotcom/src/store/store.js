import {configureStore} from '@reduxjs/toolkit';
import SearchReducer from './features/searchSlice';
import CategoryReducer from './features/categorySlice';

export const store = configureStore({
    reducer:{
        search: SearchReducer,
        category: CategoryReducer
    }
});