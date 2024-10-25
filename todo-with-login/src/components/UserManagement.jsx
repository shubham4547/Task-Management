import React, { useEffect, useState } from 'react';
import axios from 'axios';

const UserManagement = () => {
  const [users, setUsers] = useState([]);
  const [userId, setUserId] = useState('');
  const [userName, setUserName] = useState('');
  const [editingUserId, setEditingUserId] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Fetch users from API on component mount
  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const token = sessionStorage.getItem('jwtToken'); 
        console.log(token);
        const response = await axios.get('http://localhost:8080/api/todos',{
            headers: {
              'Authorization': `Bearer ${token}`, // Send the JWT in the Authorization header
            },
          }); // Replace with your API endpoint
        setUsers(response.data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchUsers();
  }, []);

  const handleAddOrUpdateUser = async (e) => {
    e.preventDefault();
    
    try {
      if (editingUserId) {
        // Update user
        await axios.put(`http://localhost:8080/api/todos/${editingUserId}`, { name: userName },{
            headers: {
              'Authorization': `Bearer ${token}`, // Send the JWT in the Authorization header
            },
          });
        setUsers((prev) =>
          prev.map((user) =>
            user.id === editingUserId ? { ...user, name: userName } : user
          )
        );
      } else {
        // Add new user
        const response = await axios.post('http://localhost:8080/api/todos', { name: userName },{
            headers: {
              'Authorization': `Bearer ${token}`, // Send the JWT in the Authorization header
            },
          });
        setUsers((prev) => [...prev, response.data]);
      }

      // Reset form
      setUserName('');
      setEditingUserId(null);
    } catch (err) {
      setError(err.message);
    }
  };

  const handleEditUser = (user) => {
    setUserName(user.name);
    setEditingUserId(user.id);
  };

  const handleDeleteUser = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/todos/${id}`,{
        headers: {
          'Authorization': `Bearer ${token}`, // Send the JWT in the Authorization header
        },
      });
      setUsers((prev) => prev.filter((user) => user.id !== id));
    } catch (err) {
      setError(err.message);
    }
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div>
      <h1>User Management</h1>
      <form onSubmit={handleAddOrUpdateUser}>
        <input
          type="text"
          placeholder="User Name"
          value={userName}
          onChange={(e) => setUserName(e.target.value)}
          required
        />
        <button type="submit">{editingUserId ? 'Update User' : 'Add User'}</button>
      </form>
      <ul>
        {users.map((user) => (
          <li key={user.id}>
            {user.name}
            <button onClick={() => handleEditUser(user)}>Edit</button>
            <button onClick={() => handleDeleteUser(user.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default UserManagement;
