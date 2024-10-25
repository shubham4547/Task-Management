import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../styles/TodoList.css';

const TodoList = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [newTask, setNewTask] = useState({ title: '', description: '', completed: false });
  const [editTask, setEditTask] = useState(null);
  const [isEditing, setIsEditing] = useState(false);

  const isAdmin = true; // Change to false to simulate non-admin user

  useEffect(() => {
    const fetchUsers = async () => {
      if (!isAdmin) {
        setError("Access denied. Only admins can view this page.");
        setLoading(false);
        return;
      }

      try {
        const token = sessionStorage.getItem('jwtToken');
        const response = await axios.get('http://localhost:8080/api/todos', {
          headers: {
            'Authorization': `Bearer ${token}`,
          },
        });
        setUsers(response.data);
      } catch (err) {
        if (err.response && err.response.status === 404) {
          setUsers([]);
          setError(null);
        } else {
          setError(err.message);
        }
      } finally {
        setLoading(false);
      }
    };

    fetchUsers();
  }, [isAdmin]);

  const handleAddTask = async () => {
    const token = sessionStorage.getItem('jwtToken');
    try {
      const response = await axios.post('http://localhost:8080/api/todos', newTask, {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });
      setUsers([...users, response.data]);
      setNewTask({ title: '', description: '', completed: false }); // Reset form
    } catch (err) {
      setError(err.message);
    }
  };

  const handleModifyTask = async () => {
    const token = sessionStorage.getItem('jwtToken');
    try {
      const response = await axios.put(`http://localhost:8080/api/todos/${editTask.id}`, editTask, {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });
      setUsers(users.map(task => (task.id === editTask.id ? response.data : task)));
      resetForm();
    } catch (err) {
      setError(err.message);
    }
  };

  const handleDeleteTask = async (taskId) => {
    const token = sessionStorage.getItem('jwtToken');
    try {
      await axios.delete(`http://localhost:8080/api/todos/${taskId}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });
      setUsers(users.filter(task => task.id !== taskId));
    } catch (err) {
      setError(err.message);
    }
  };

  const startEditing = (task) => {
    setEditTask(task);
    setNewTask({ title: task.title, description: task.description, completed: task.completed });
    setIsEditing(true);
  };

  const resetForm = () => {
    setNewTask({ title: '', description: '', completed: false });
    setEditTask(null);
    setIsEditing(false);
  };

  const handleStatusChange = (e) => {
    const isCompleted = e.target.value === 'Completed';
    if (isEditing) {
      setEditTask({ ...editTask, completed: isCompleted });
    } else {
      setNewTask({ ...newTask, completed: isCompleted });
    }
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div className="container">
      <h1>Task List</h1>

      <div className="form-group">
        <input
          type="text"
          placeholder="Title"
          value={isEditing ? editTask.title : newTask.title}
          onChange={(e) => isEditing ? setEditTask({ ...editTask, title: e.target.value }) : setNewTask({ ...newTask, title: e.target.value })}
        />
        <input
          type="text"
          placeholder="Description"
          value={isEditing ? editTask.description : newTask.description}
          onChange={(e) => isEditing ? setEditTask({ ...editTask, description: e.target.value }) : setNewTask({ ...newTask, description: e.target.value })}
          rows="4"
          className="description-input"
        />
        <select value={isEditing ? (editTask.completed ? 'Completed' : 'Pending') : (newTask.completed ? 'Completed' : 'Pending')} onChange={handleStatusChange}>
          <option value="Pending">Pending</option>
          <option value="Completed">Completed</option>
        </select>
        <div className="button-container">
          {isEditing ? (
            <>
              <button onClick={handleModifyTask}>Save</button>
              <button onClick={resetForm}>Cancel</button>
            </>
          ) : (
            <button onClick={handleAddTask}>Add</button>
          )}
        </div>
      </div>

      <table className="table">
        <thead>
          <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Status</th>
            <th>Create Time</th>
            <th>Update Time</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {users.map(todo => (
            <tr key={todo.id}>
              <td>{todo.title}</td>
              <td>{todo.description}</td>
              <td>{todo.completed ? 'Completed' : 'Pending'}</td>
              <td>{todo.createdAt}</td>
              <td>{todo.updatedAt}</td>
              <td className="button-container">
                <button onClick={() => startEditing(todo)}>Modify</button>
                <button onClick={() => handleDeleteTask(todo.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default TodoList;
