import React, { useState } from 'react';

const CreateTask = ({ onAddTask, onClose }) => {
  const [taskName, setTaskName] = useState('');
  const [taskDescription, setTaskDescription] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    if (taskName && taskDescription) {
      onAddTask({
        name: taskName,
        description: taskDescription,
        status: 'To Do',
      });
      setTaskName('');
      setTaskDescription('');
      onClose();
    }
  };

  return (
    <div>
      <h2>Create Task</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Name:</label>
          <input
            type="text"
            value={taskName}
            onChange={(e) => setTaskName(e.target.value)}
            placeholder="Task Name"
          />
        </div>
        <div>
          <label>Description:</label>
          <input
            type="text"
            value={taskDescription}
            onChange={(e) => setTaskDescription(e.target.value)}
            placeholder="Task Description"
          />
        </div>
        <button type="submit">Add Task</button>
        <button type="button" class="cancel" onClick={onClose}>
          Cancel
        </button>
      </form>
    </div>
  );
};

export default CreateTask;
