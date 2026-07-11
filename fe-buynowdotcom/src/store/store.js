import {configureStore} from '@reduxjs/toolkit';
import SearchReducer from './features/searchSlice';

export const store = configureStore({
    reducer:{
        search: SearchReducer,
    }
});