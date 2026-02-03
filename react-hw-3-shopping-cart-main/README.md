# Shopping Cart - React Application

## Goal

Create a shopping cart using React **functional components**, **Redux**, and **react-router-dom**. The application allows users to:

- **View all products**: Displays a list of products with their name, price, and description.
- **Add items to the cart**: Users can add products to the shopping cart, including handling quantities.
- **Adjust quantities**: Increase or decrease the quantity of a specific product in the cart.
- **Remove items**: Remove individual products from the cart entirely.
- **View the cart summary**: Displays the total quantity of items and the total price in real-time.
- **Handle 404 routes**: Navigating to a non-existent route displays a custom 404 page.
- **Fetch product data**: Simulates fetching products from a sample API using Axios.

## Features

- **State Management**: Uses Redux for managing the application's state, including cart management.
- **Routing**: Implements client-side routing with React Router DOM to handle navigation and a custom 404 page.
- **API Requests**: Integrates Axios for making HTTP requests to interact with external APIs.
- **Dynamic UI Updates**: Add, remove, and delete items in the cart with real-time updates.
- **Error Handling**: Displays a custom 404 page for undefined routes.

## TODO

### 1. React Router

#### `src/App.jsx`

- Set up the routing structure for the application using `react-router-dom`. Ensure all pages, such as `Cart` and `ProductList`, have corresponding routes.
- Create and configure a custom 404 page (`NotFound.jsx`) to handle unknown routes, providing a user-friendly error message or navigation back to the home page.

---

### 2. Redux State Management

#### `src/store/cartSlice/cart.slice.js`

- Implement a Redux slice to manage cart-related business logic, such as adding and removing items from the cart.
- Calculate and maintain `totalPrice` and `totalQuantity` in the reducers to ensure the cart state is accurate and up-to-date.
- Use `useDispatch` and `useSelector` hooks to manage state effectively within the components.

---

### 3. API Integration with Axios

#### `src/interceptors/auth.interceptor.js`

- Configure an Axios instance with a base URL for API requests, ensuring consistent endpoints throughout the application.
- Add request and response interceptors to handle tasks such as attaching authentication tokens and managing API errors gracefully.

#### Fetching Product Data

- Implement separate functions to fetch product data from two different APIs: one for the `ProductList` page and another for the `ProductDetail` page.
- Handle loading states and errors during the API call, providing feedback to the user if the data fails to load or is delayed.

## Instructions

### 1. Fork the Project

- Click the **[Fork this project on StackBlitz](https://stackblitz.com/edit/vitejs-vite-27yatkye)** link to create a copy of the project in your StackBlitz account.

### 2. Install Dependencies

- Once you’ve forked the project, open the project in StackBlitz or your local development environment.
- Run `npm install` to install the required dependencies.

### 3. Run the Application

- Run the application locally using `npm run dev`, or if using StackBlitz, it will automatically start a live preview.
- The task manager page will load with tasks displayed from the API, and you can start interacting with the application.

### 4. Complete the Functions to make the application work

## Code Structure

```bash
src/
├── components/
│   ├── ProductItem.jsx     // Reusable component to display a single product
│   └── CartSummary.jsx     // Shows the current cart status, including item count and total price
├── pages/
│   ├── Cart.jsx            // Handles the cart view and item management
│   ├── ProductList.jsx     // Displays a list of available products
│   └── ProductDetail.jsx   // Displays detailed information about a product
├── store/
│   ├── store.js            // Configures the Redux store
│   └── cartSlice/
│       └── cart.slice.js   // Defines the Redux slice for cart state management
├── interceptors/
│   └── auth.interceptor.js    // Creates an Axios instance with interceptors for API requests
└── App.jsx                 // Main application component and route configuration
```

## Setup Instructions

1. **Fork this project**:

   - Click the **[Fork this project on StackBlitz](https://stackblitz.com/edit/vitejs-vite-27yatkye)** link to create a copy of the project in your StackBlitz account.

2. **Start Editing**:

   - Once you’ve forked the project, you can start editing directly in the StackBlitz editor or your local development environment.

3. **Live Preview**:

   - StackBlitz will automatically start a live preview for you, allowing you to see the app running in the browser.

4. **Make Your Changes**:
   - Implement the required functionality as described in the instructions.

---

## Notes

- Uses **Redux** and **react-router-dom** to help you complete this project.
- Be sure to structure your code according to React's best practices for functional components.
- Maintain a clear separation of concerns:
  - **components/**: Focus on UI logic and rendering.
  - **store/**: Centralize all state management and avoid coupling it with UI components.
  - **utils/**: Place reusable utilities like API instances or helper functions.

## Submission Instructions

- **Copy your StackBlitz link** after completing the project.
- **Submit the link to Drill** for grading.
