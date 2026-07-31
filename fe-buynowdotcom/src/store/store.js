import {configureStore} from '@reduxjs/toolkit';
import SearchReducer from './features/searchSlice';
import CategoryReducer from './features/categorySlice';
import productReducer from './features/productSlice';
import paginationReducer from './features/paginationSlice';
import cartReducer from './features/cartSlice';
import orderReducer from './features/orderSlice'

export const store = configureStore({
    reducer:{
        search: SearchReducer,
        category: CategoryReducer,
        product: productReducer,
        pagination: paginationReducer,
        cart: cartReducer,
        order: orderReducer
    }
});