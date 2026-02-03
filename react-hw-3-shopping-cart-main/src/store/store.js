import { configureStore } from '@reduxjs/toolkit';
import cartReducer from './cartSlice/cart.slice.js';

const store = configureStore({
  reducer: {
    cart: cartReducer,
  },
});

export default store;
