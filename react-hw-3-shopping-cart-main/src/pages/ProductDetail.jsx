import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import ProductItem from '../components/ProductItem.jsx';
import axiosInstance from '../interceptors/auth.interceptor.js';
import './ProductDetail.css';

const ProductDetail = () => {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchProduct = async () => {
    try {
      const response = await axiosInstance.get(`/products/${id}`);
      setProduct(response.data);
      setLoading(false);
    } catch (err) {
      setError('Failed to fetch product details.');
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProduct();
  }, [id]);

  if (loading) {
    return <p>Loading product details...</p>;
  }

  if (error) {
    return <p style={{ color: 'red' }}>{error}</p>;
  }

  return (
    <div>
      <Link to="/" className="back-link">
        ← Back to Products
      </Link>
      <div className="product-detail-container">
        <h2>{product.title}</h2>
        <ProductItem key={product.id} product={product} />
        <div className="product-description">
          <p>
            <strong>Description:</strong> {product.description}
          </p>
          <p>
            <strong>Rating:</strong> {product.rating} / 5
          </p>
          <p>
            <strong>Return Policy:</strong> {product.returnPolicy}
          </p>
          <p>
            <strong>Warranty Information:</strong> {product.warrantyInformation}
          </p>
          <p>
            <strong>Shipping Information:</strong> {product.shippingInformation}
          </p>
        </div>
      </div>
    </div>
  );
};

export default ProductDetail;
