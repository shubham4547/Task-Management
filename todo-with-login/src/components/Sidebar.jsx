import React from 'react';
import { NavLink } from 'react-router-dom';
import '../styles/Sidebar.css'; // Optional: import CSS for styling

const Sidebar = () => {
  return (
    <div className="sidebar">
      <ul>
        <li>
          <NavLink to="/" exact activeClassName="active">Dashboard</NavLink>
        </li>
        <li>
          <NavLink to="/profile" activeClassName="active">Profile</NavLink>
        </li>
        <li>
          <NavLink to="/todolist" activeClassName="active">Task List</NavLink>
        </li>
        <li>
          <NavLink to="/help" activeClassName="active">Help</NavLink>
        </li>
      </ul>
    </div>
  );
};

export default Sidebar;
