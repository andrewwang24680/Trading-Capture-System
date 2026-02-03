import React, { Component } from 'react';

class ProductTable extends Component {
  constructor(props) {
    super(props);
    this.state = {
      products: this.props.initialProducts,
      newProductName: '',
    };
  }

  handleAddProduct = () => {
    const newProduct = {
      id: this.state.products.length + 1,
      name: this.state.newProductName,
    };
    this.setState((prevState) => ({
      products: [...prevState.products, newProduct],
      newProductName: '',
    }));
  };

  handleDeleteProduct = (id) => {
    this.setState((prevState) => ({
      products: prevState.products.filter((product) => product.id !== id),
    }));
  };

  handleInputChange = (e) => {
    this.setState({ newProductName: e.target.value });
  };

  render() {
    return (
      <div>
        <h1>Product Management</h1>

        <input
          type="text"
          value={this.state.newProductName}
          onChange={this.handleInputChange}
          placeholder="Enter product name"
        />
        <button onClick={this.handleAddProduct}>Add Product</button>

        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Product Name</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {this.state.products.map((product) => (
              <tr key={product.id}>
                <td>{product.id}</td>
                <td>{product.name}</td>
                <td>
                  <button onClick={() => this.handleDeleteProduct(product.id)}>
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}
export default ProductTable;
