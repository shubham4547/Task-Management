// src/components/Registration.jsx
import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
//import '../styles/App.css';
import '../styles/Register.css'

const Registration = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    username: '',
    password: '',
    email: '',
    mobileNumber: '',
    age: '',
    gender: ''
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setSuccess(false);

    try {
      const response = await axios.post('http://localhost:8080/api/register', formData);
      console.log('Registration Successful:', response.data);
      setSuccess(true);
      navigate('/login');
    } catch (err) {
      console.error('Registration Error:', err);
      setError('Registration failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <h1>Register</h1>
      <form onSubmit={handleSubmit}>
        <label>First Name :</label>
        <input
          type="text"
          name="firstName" // Changed from "name" to "firstname"
          value={formData.firstName}
          onChange={handleChange}
          required
        />
        <label>Last Name :</label>
        <input
          type="text" // Changed from "tel" to "text"
          name="lastName" // Changed from "mobileNumber" to "lastname"
          value={formData.lastName}
          onChange={handleChange}
          required
        />
        <label>Username:</label>
        <input
          type="text"
          name="username"
          value={formData.username}
          onChange={handleChange}
          required
        />
        <label>Password:</label>
        <input
          type="password"
          name="password"
          value={formData.password}
          onChange={handleChange}
          required
        />
        <label>Email:</label>
        <input
          type="email"
          name="email"
          value={formData.email}
          onChange={handleChange}
          required
        />
        <label>Phone Number:</label>
        <input
          type="tel"
          name="mobileNumber"
          value={formData.mobileNumber}
          onChange={handleChange}
          required
        />
        <label>Age :</label>
        <input
          type="number" // Changed from "tel" to "number"
          name="age" // Changed from "mobileNumber" to "age"
          value={formData.age}
          onChange={handleChange}
          required
        />
        <label>Gender :</label>
        <input
          type="text" // Changed from "tel" to "text"
          name="gender" // Changed from "mobileNumber" to "gender"
          value={formData.gender}
          onChange={handleChange}
          required
        />
        
        <button type="submit" disabled={loading}>
          {loading ? 'Registering...' : 'Register'}
        </button>
      </form>
      {error && <p className="error">{error}</p>}
      {success && <p className="success">Registration successful!</p>}
    </div>
  );
};

export default Registration;
