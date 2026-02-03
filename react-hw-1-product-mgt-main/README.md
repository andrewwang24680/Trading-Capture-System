# Product Management Page - React Assignment

## Goal

Create a product management page using React class components or functional components. The page should allow users to:

- View a list of products.
- Add new products to the list.
- Delete existing products from the list.
- When the page is first loaded, three products should be displayed.

## Instructions

### 1. Fork the Project

- Click the **[Fork this project on StackBlitz](https://stackblitz.com/edit/vitejs-vite-yuceeyra)** link to create a copy of the project in your StackBlitz account.

### 2. Create a React Component

- Create a component named `ProductTable`.
- In this component, you will:
  - **Receive an initial list of products via props** (passed from the parent component).
  - **Use the `state` to manage the list of products**.

### 3. Implement Product List Display

- In the `render()` method, display a table with the following columns:
  - Product ID
  - Product Name
  - Action (Delete button)

### 4. Add Product Functionality

- Implement an input field and a button to allow users to add a new product.
  - When the user types a product name and clicks "Add Product," a new product should be added to the product list (ensure it has a unique `id`).

### 5. Delete Product Functionality

- Each product in the list should have a "Delete" button.
  - When the user clicks on the "Delete" button, the corresponding product should be removed from the list.

### 6. Initial Data

- When the page first loads, there should be three products displayed in the table. These products will be passed into the component via props from the parent component.

### 7. React Component Lifecycle

- Use lifecycle methods where appropriate (e.g., `constructor`, `useState`, etc.) to manage state and handle events.

### 8. Bonus Challenge (Optional)

- Try to add more advanced features like:
  - Validation on the product name (e.g., ensure it's not empty).
  - Updating the product name after it’s been added (e.g., edit product feature).

---

## Setup Instructions

1. **Fork this project**:

   - Click the **[Fork this project on StackBlitz](https://stackblitz.com/edit/vitejs-vite-yuceeyra)** link to create a copy of the project in your StackBlitz account.

2. **Start Editing**:

   - Once you’ve forked the project, you can start editing directly in the StackBlitz editor.

3. **Live Preview**:

   - StackBlitz automatically starts a live preview for you, and you can see the app running in the browser. The product management page should load automatically.

4. **Make Your Changes**:
   - Implement the required functionality as described in the instructions.

---

## Notes

- Remember to use React's `state` to store and manage the list of products.
- The initial list of products will be passed in via props when the page loads.
- You can use simple buttons to delete products from the list.
- Be sure to structure your code according to React's best practices for class components or functional components.

## Submission Instructions

- **Copy your StackBlitz link** after completing the project.
- **Submit the link to Drill** for grading.
