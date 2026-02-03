import React from 'react';
import { Link } from 'react-router-dom';
import './CartSummary.css';
import { useSelector } from 'react-redux';

const CartSummary = () => {
  const { totalQuantity, totalPrice } = useSelector((state) => state.cart);

  return (
    <div className="cart-summary">
      <h3 className="cart-summary-text">Total Items: {totalQuantity}</h3>
      <h3 className="cart-summary-text">
        Total Price: {totalPrice.toFixed(2)}
      </h3>

      <Link to="/cart" className="cart-link">
        Go to Cart
      </Link>
    </div>
  );
};

export default CartSummary;
