# Project Requirements

## Overview
You will complete the implementation of a basic blog application with the following features:
1. Login
2. Register
3. View and Add Blogs

The project includes three Servlets, two DAOs, and two HTML files. Your goal is to complete the missing logic and make the application functional.

---

## Servlets

### 1. HomeServlet
- **Purpose**: Display all blog posts and allow users to add a new blog using `doPost()`.
- **Status**: Fully implemented. No changes are needed.
- **Details**: Displays a table of blog posts and a form to create a new blog. Adding blogs works only if the user is logged in.

### 2. RegisterServlet
- **Purpose**: Handle user registration.
- **Tasks**:
    - Configure the servlet name and path either using annotations or XML.
        - **Key Point**: Ensure the servlet path matches the action attribute in `register.html`. When the "Submit" button in the registration form is clicked, it should correctly call `RegisterServlet`.
    - Implement the `doPost()` method with the following logic:
        - Trigger the provided method from `UserDao` to add the user.
        - Redirect the user:
            - If the registration is successful, redirect to `login.html`.
            - If registration fails, remain on `register.html` and append `?error=true` as a query parameter to indicate the error. (The logic for displaying the error is already implemented in `register.html`; your task is to ensure the error is passed correctly.)

### 3. LoginServlet
- **Purpose**: Handle user login.
- **Tasks**:
    - Configure the servlet name and path either using annotations or XML.
        - **Key Point**: Ensure the servlet path matches the action attribute in `login.html`. When the "Submit" button in the login form is clicked, it should correctly call `LoginServlet`.
    - Implement the `doPost()` method with the following logic:
        - Validate the username and password using the provided method from `UserDao` to check if they are correct.
        - Maintain the login status for the current user. This will impact how you implement the logic in your filter and how it should integrate with the provided code in `HomeServlet`.
        - Redirect the user:
            - If login fails, redirect to `login.html` and append `error=true` query parameter to indicate a login error. (The logic for displaying the error is already implemented in `login.html`; your task is to ensure the error is passed correctly.)
            - If login succeeds, redirect to the home page.

---

## DAO Classes

### 1. UserDao
- **Current Status**: Stores user data in memory at the class level.
- **Limitation**: Since `RegisterServlet` and `LoginServlet` create separate instances, user data is not shared. As a result, users registered via `RegisterServlet` cannot log in using `LoginServlet`.

### 2. Bonus Challenge:
Modify the `UserDao` and `BlogDao` classes to connect to the database (using JDBC).
- Use the provided database connection for consistency across environments.
- Avoid connecting to your local database.
- If you don't have time to complete this, the current in-memory logic will work as a fallback.

### 3. BlogDao
- **Current Status**: Stores blog data in memory at the class level.
- **Task**: No changes required unless you tackle the Bonus Challenge.

---

## HTML Files

### 1. login.html
- Provides a form for user login. Submits to `LoginServlet`.
- No changes needed.

### 2. register.html
- Provides a form for user registration. Submits to `RegisterServlet`.
- No changes needed.

---

## Additional Security

To protect the Home page (`HomeServlet`), you will implement a Filter:
- **Task**: Complete the Filter to check the user’s login status.
- **Behavior**:
    - If the user is not logged in, redirect them to `login.html`.
    - The filter should preserve the original target path so users can return to it after logging in.

---

## Summary of Tasks
1. Complete the `doPost()` logic for `RegisterServlet`.
2. Complete the `doPost()` logic for `LoginServlet`.
3. Implement the Filter to secure the Home page.
4. **Bonus Challenge**: Connect `UserDao` and `BlogDao` to a database using JDBC.