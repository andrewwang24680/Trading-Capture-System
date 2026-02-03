import React, { useEffect, useState } from 'react';
import ProductItem from '../components/ProductItem.jsx';
import CartSummary from '../components/CartSummary.jsx';
import './ProductList.css';
import axiosInstance from '../interceptors/auth.interceptor.js';
const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchProducts = async () => {
    try {
      const response = await axiosInstance.get('/products');
      setProducts(response.data.products);
      setLoading(false);
    } catch (err) {
      setError('Failed to fetch products.');
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  if (loading) {
    return <p>Loading products...</p>;
  }

  if (error) {
    return <p style={{ color: 'red' }}>{error}</p>;
  }

  return (
    <div className="product-list-container">
      <h2 className="product-list-title">Products</h2>
      <CartSummary />
      <ul className="product-list">
        {products.map((product) => (
          <ProductItem key={product.id} product={product} />
        ))}
      </ul>
    </div>
  );
};

export default ProductList;
