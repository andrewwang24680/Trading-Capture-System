import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import {
  removeItemFromCart,
  clearCart,
} from '../store/cartSlice/cart.slice.js';
import { Link } from 'react-router-dom';
import './Cart.css';

const Cart = () => {
  const { totalQuantity, totalPrice, items } = useSelector((state) => state.cart);
  const dispatch = useDispatch();

  const handleRemoveItemFromCart = (id) => {
    dispatch(removeItemFromCart(id));
  };

  const handleClearCart = () => {
    dispatch(clearCart());
  };

  return (
    <div className="cart-container">
      <h2 className="cart-title">Shopping Cart</h2>
      {items.length === 0 ? (
        <div className="empty-cart-message">
          <p>Your cart is empty.</p>
          <Link to="/" className="go-shopping-link">
            Go Shopping
          </Link>
        </div>
      ) : (
        <>
          <ul className="cart-items-list">
            {items.map((item) => (
              <li key={item.id} className="cart-item">
                <img
                  src={item.thumbnail}
                  alt={item.title}
                  className="cart-item-image"
                />
                <div className="cart-item-details">
                  <h3>{item.title}</h3>
                  <p>
                    <strong>Price:</strong> ${item.price}
                  </p>
                  <p>
                    <strong>Quantity:</strong> {item.quantity}
                  </p>
                </div>
                <button
                  onClick={() => handleRemoveItemFromCart(item.id)}
                  className="remove-item-button"
                >
                  Remove
                </button>
              </li>
            ))}
          </ul>

          <div className="cart-summary">
            <p className="total-items">Total Items: {totalQuantity}</p>
            <p className="total-items">Total Price: ${totalPrice.toFixed(2)}</p>
            <div>
              <button onClick={handleClearCart} className="clear-cart-button">
                Clear Cart
              </button>
              <Link to="/" className="back-to-products-link">
                Back to Products
              </Link>
            </div>
          </div>
        </>
      )}
    </div>
  );
};

export default Cart;
