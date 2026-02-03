import React from 'react';
import { Link } from 'react-router-dom';
import './ProductItem.css';
import { useDispatch, useSelector } from 'react-redux';
import {
  increaseItemQuantity,
  decreaseItemQuantity,
} from '../store/cartSlice/cart.slice.js';
const ProductItem = ({ product }) => {
  const dispatch = useDispatch();
  const cart = useSelector((state) => state.cart.items);

  const isProductInCart = (productId) => {
    return cart.find((item) => item.id === productId);
  };

  const getProductQuantity = (productId) => {
    const productInCart = isProductInCart(productId);
    return productInCart ? productInCart.quantity : 0;
  };

  return (
    <li className="product-item">
      <Link to={`/product/${product.id}`} className="product-link">
        <img
          src={product.thumbnail}
          alt={product.title}
          className="product-image"
        />
        <h3 className="product-title">{product.title}</h3>
      </Link>
      <p className="product-price">Price: ${product.price}</p>

      {isProductInCart(product.id) ? (
        <div className="product-quantity-container">
          <button
            onClick={() => dispatch(decreaseItemQuantity(product))}
            className="quantity-button remove-button"
          >
            -
          </button>
          <p className="quantity-text">
            Quantity: {getProductQuantity(product.id)}
          </p>
          <button
            onClick={() => dispatch(increaseItemQuantity(product))}
            className="quantity-button add-button"
          >
            +
          </button>
        </div>
      ) : (
        <button
          onClick={() => dispatch(increaseItemQuantity(product))}
          className="add-to-cart-button"
        >
          Add to Cart
        </button>
      )}
    </li>
  );
};

export default ProductItem;
