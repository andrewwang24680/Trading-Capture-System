import { createSlice } from "@reduxjs/toolkit";

const updateCartTotals = (state) => {
  state.totalQuantity = state.items.reduce(
    (total, item) => total + item.quantity,
    0
  );
  state.totalPrice = state.items.reduce(
    (total, item) => total + item.price * item.quantity,
    0
  );
};

const cartSlice = createSlice({
  name: "cart",
  initialState: {
    items: [],
    totalQuantity: 0,
    totalPrice: 0,
  },
  reducers: {
    increaseItemQuantity(state, action) {
      const existingItem = state.items.find(
        (item) => item.id === action.payload.id
      );
      if (existingItem) {
        existingItem.quantity += 1;
      } else {
        state.items.push({ ...action.payload, quantity: 1 });
      }
      updateCartTotals(state);
    },
    decreaseItemQuantity(state, action) {
      const existingItem = state.items.find(
        (item) => item.id === action.payload.id
      );
      if (existingItem) {
        existingItem.quantity -= 1;
        updateCartTotals(state);

        if (existingItem.quantity === 0) {
          state.items = state.items.filter(
            (item) => item.id !== existingItem.id
          );
        }
      }
    },
    removeItemFromCart(state, action) {
      state.items = state.items.filter((item) => item.id !== action.payload);
      updateCartTotals(state);
    },
    clearCart: (state) => {
      state.items = [];
      updateCartTotals(state);
    },
  },
});

export const {
  increaseItemQuantity,
  decreaseItemQuantity,
  removeItemFromCart,
  clearCart,
} = cartSlice.actions;
export default cartSlice.reducer;
