# Task Manager - React Application

## Goal

Create a task management page using React **functional components**. The page allows users to:

- View all tasks with their name, description, and status.
- Add new tasks to the list with a name and description.
- Toggle the status of tasks between "To Do" and "Completed".
- Strike through the text for tasks marked as "Completed".
- The page initially displays tasks fetched from a sample API.

## Features

- **Task List**: Displays a list of tasks with their **name**, **description**, and **status**.
  - **name**: The name of the task.
  - **description**: A short description for the task.
  - **status**: The current status of the task, which can either be "To Do" or "Completed".
  - Each task has an option to toggle the status between "To Do" and "Completed".
- **Task Creation**: Allows users to add new tasks to the list with custom **name** and **description**. When the task is first created, its id should be assigned to the total task length + 1 and the status is set to "To Do".
- **Toggle Task Status**: Users can change the task status between "To Do" and "Completed".
- **CSS for Completed Tasks**: Completed tasks are visually distinguished by a strikethrough effect.

## Components

### TaskTable

This component is responsible for displaying the task list and handling the task status toggle.

- **State Management**: Tasks are stored in state and can be updated using a reducer.
  - When a task is at "Completed" status, a className="completed" should be assigned to this row so a strike-through CSS will be applied to this row to show a CSS effect.
- **Task Status Toggle**: A button allows users to toggle between "To Do" and "Completed" statuses for each task.
- We have a button on the top of the table to demonstrate whether the **CreateTask** component is displayed.
- Initially, TaskTable should fetch only **5** top results from this API when the component first loads: [https://jsonplaceholder.typicode.com/todos](https://jsonplaceholder.typicode.com/todos).
  - For each `todo` fetched from the API, construct a `task` object by mapping the `todo` fields:
    - `id`: Map to `todo.id`.
    - `name`: Set as the string `'Task {id}'`, where `{id}` is the `todo.id`.
    - `description`: Map to `todo.title`.
    - `status`: Map to `todo.completed`:
      - If `completed` is `true`, set the `status` to `"Completed"`.
      - If `completed` is `false`, set the `status` to `"To Do"`.

### CreateTask

This component handles the creation of new tasks. It is displayed when the "Create Task" button is clicked.

- **Task Creation**: Users can enter the task **name** and **description** and add it to the task list.
- **Form Validation**: The form ensures that both the task name and description are provided before submission.
- A Cancel button should be provided so we can hide the current component and cancel this action.

## Instructions

### 1. Fork the Project

- Click the **[Fork this project on StackBlitz](https://stackblitz.com/edit/vitejs-vite-8kpvgxzr)** link to create a copy of the project in your StackBlitz account.

### 2. Install Dependencies

- Once you’ve forked the project, open the project in StackBlitz or your local development environment.
- Run `npm install` to install the required dependencies.

### 3. Run the Application

- Run the application locally using `npm run dev`, or if using StackBlitz, it will automatically start a live preview.
- The task manager page will load with tasks displayed from the API, and you can start interacting with the application.

### 4. Complete the Functions to make the application work

## Code Structure

- **src**: Contains the `TaskTable` and `CreateTask` components.
  - `TaskTable.jsx`: Displays the task list and manages state using a reducer.
  - `CreateTask.jsx`: Form for adding new tasks.

## Setup Instructions

1. **Fork this project**:

   - Click the **[Fork this project on StackBlitz](https://stackblitz.com/edit/vitejs-vite-8kpvgxzr)** link to create a copy of the project in your StackBlitz account.

2. **Start Editing**:

   - Once you’ve forked the project, you can start editing directly in the StackBlitz editor or your local development environment.

3. **Live Preview**:

   - StackBlitz will automatically start a live preview for you, allowing you to see the app running in the browser.

4. **Make Your Changes**:
   - Implement the required functionality as described in the instructions.

---

## Notes

- Uses **React functional components** and **hooks** to help you achieve the functionalities (`useState`, `useReducer`, `useEffect`).
- Be sure to structure your code according to React's best practices for functional components.

## Submission Instructions

- **Copy your StackBlitz link** after completing the project.
- **Submit the link to Drill** for grading.
