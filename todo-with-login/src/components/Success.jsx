// src/components/Success.jsx
import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/App.css'; // Import your styles
//import UserList from './UserList';

const Success = () => {
    const navigate = useNavigate();
    const handleLogout = () => {
        // Clear user data from local storage or state management
        localStorage.removeItem('user'); // Adjust according to your storage method
        // Redirect to login page
        navigate('/login');
      };
  return (
    <div className="container">
      <h1>Login Successful!</h1>
      <p>Welcome back! You are now logged in.</p>
      <p>Feel free to explore the application.</p>
      <button onClick={handleLogout}>Logout</button>
      {/* You can add more navigation options here 
 //     <UserList/>*/}
    </div>
  );
};

export default Success;