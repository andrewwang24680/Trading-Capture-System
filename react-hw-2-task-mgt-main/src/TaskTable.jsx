import React, { useState, useEffect, useReducer } from 'react';

import CreateTask from './CreateTask.jsx';

// Reducer for task state
const taskReducer = (state, action) => {
  switch (action.type) {
    case 'task/add':
      return [...state, ...action.payload];
    case 'task/toggleStatus':
      const updatedTasks = state.map((task) =>
        task.id === action.payload
          ? { ...task, status: task.status === 'To Do' ? 'Completed' : 'To Do' }
          : task
      );
      return updatedTasks;
    default:
      return state;
  }
};

const TaskTable = () => {
  const [tasks, taskDispatch] = useReducer(taskReducer, []);
  const [showForm, setShowForm] = useState(false);

  // useEffect to fetch task data
  useEffect(() => {
    fetch('https://jsonplaceholder.typicode.com/todos')
      .then((res) => res.json())
      .then((data) => {
        const initialTasks = data.slice(0, 5).map((task) => ({
          id: task.id,
          name: `TASK ${task.id}`,
          description: task.title,
          status: task.completed ? 'Completed' : 'To Do',
        }));
        taskDispatch({ type: 'task/add', payload: initialTasks });
      });
  }, []);

  const addTask = (newTask) => {
    const taskWithId = { ...newTask, id: tasks.length + 1 };
    taskDispatch({ type: 'task/add', payload: [taskWithId] });
  };

  const toggleStatus = (taskId) => {
    taskDispatch({ type: 'task/toggleStatus', payload: taskId });
  };

  return (
    <div>
      <h1>Task Manager</h1>
      <button onClick={() => setShowForm(true)} disabled={showForm}>
        Create Task
      </button>
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Status</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {tasks.map((task) => (
            <tr
              key={task.id}
              className={task.status === 'Completed' ? 'completed' : ''}
            >
              <td>{task.name}</td>
              <td>{task.description}</td>
              <td>{task.status}</td>
              <td>
                <button onClick={() => toggleStatus(task.id)}>
                  Mark as {task.status === 'To Do' ? 'Completed' : 'To Do'}
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {showForm && (
        <CreateTask onAddTask={addTask} onClose={() => setShowForm(false)} />
      )}
    </div>
  );
};

export default TaskTable;
