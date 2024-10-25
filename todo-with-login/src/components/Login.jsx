// src/components/Login.jsx
import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../styles/App.css';

const Login = ({}) => {
  const navigate = useNavigate();
  const [credentials, setCredentials] = useState({
    username: '',
    password: '',
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCredentials({ ...credentials, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setSuccess(false);

    try { 
      const response = await axios.post('http://localhost:8080/api/login', credentials);
      console.log('Login Successful:', response.data);
       // Extract the jwtToken and username from the response
      const { jwtToken, username } = response.data; 
      sessionStorage.setItem('jwtToken', jwtToken); // Store the token in session storage
      console.log('Stored Token:', jwtToken);
      // Optionally, store the username if needed
      sessionStorage.setItem('username', username); // Store the username
      setSuccess(true);
      //setIsLoggedIn(true);
      // navigate('/success')
      navigate('/todolist')
    } catch (err) {
      console.error('Login Error:', err);
      setError('Login failed. Please check your credentials.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <h1>Login</h1>
      <form onSubmit={handleSubmit}>
        <label>Username:</label>
        <input
          type="text"
          name="username"
          value={credentials.username}
          onChange={handleChange}
          required
        />
        <label>Password:</label>
        <input
          type="password"
          name="password"
          value={credentials.password}
          onChange={handleChange}
          required
        />
        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default Login;
