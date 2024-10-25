import React from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Logout = ({ onLogout }) => {
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      // Call the logout API
      const token = sessionStorage.getItem('jwtToken');
      if (token) {
        await axios.post('http://localhost:8080/api/logout', {}, {
          headers: {
            'Authorization': `Bearer ${token}`,
          },
        });
      }
      
      // Clear the token from session storage
      sessionStorage.removeItem('jwtToken');

      // Optionally call the onLogout function to update the state in the parent component
      if (onLogout) {
        await onLogout();
      }

      // Redirect to the login page
      navigate('/login');
    } catch (error) {
      console.error('Logout failed:', error);
      // You can add user feedback here, e.g., an alert
      alert('Logout failed. Please try again.');
    }
  };

  return (
    <button onClick={handleLogout}>
      Logout
    </button>
  );
};

export default Logout;

