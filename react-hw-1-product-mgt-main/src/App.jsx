import { useState } from 'react';
import './App.css';
import ProductTable from './ProductTable';

function App() {
  const initialProducts = [
    { id: 1, name: 'Apple' },
    { id: 2, name: 'Banana' },
    { id: 3, name: 'Car' },
  ];

  return (
    <>
      <ProductTable initialProducts={initialProducts} />
    </>
  );
}

export default App;
