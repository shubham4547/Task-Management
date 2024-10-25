import React,{ useState }from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.css';
// Put any other imports below so that CSS from your
// components takes precedence over default styles.
import Registration from './components/Registration';
import Login from './components/Login';
import Logout from './components/Logout';
import Success from './components/success';
import './styles/App.css';
import './styles/Register.css';
import TodoList from './components/todoList';
import MyProfile from './components/MyProfile';


  
  
const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false); // Change this based on your auth logic

  const handleLogout = () => {
    // Perform logout logic here (e.g., clearing tokens, making API call)
    setIsLoggedIn(false);

  };
  return (
    <Router>
      <div>
       { <nav>
          <Link to="/register">Register</Link>
          <Link to="/login">Login</Link>
          <Link to="/profile">My Profile</Link>
          <Link to="/todolist">Task List</Link>
          <Link to="/logout">Logout</Link>
        </nav>}  
        <Routes>
          <Route path="/register" element={<Registration />} />
          <Route path="/login" element={<Login />} />
          <Route path="/success" element={<Success />} />
          <Route path="/todolist" element={<TodoList />} />
          <Route path="/profile" element={<MyProfile />} />
          <Route path="/logout" element={<Logout />} />
          <Route path="/" element={
            <div className="container">
              <h1>Welcome to Task Management</h1>
            </div>
          } />
        </Routes>
      </div>
    </Router>
  );
};

export default App;