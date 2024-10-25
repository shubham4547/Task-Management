import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../styles/MyProfile.css'; // Import the CSS file

const MyProfile = () => {
    const [profile, setProfile] = useState({
        username: '',
        firstName: '',
        lastName: '',
        email: '',
        gender: '',
        age: '',
        mobileNumber: '',
        password: '', // Added password field
    });
    const [error, setError] = useState('');
    const [successMessage, setSuccessMessage] = useState('');

    useEffect(() => {
        const fetchProfile = async () => {
            const token = sessionStorage.getItem('jwtToken');
            try {
                const response = await axios.get('http://localhost:8080/api', {
                    headers: {
                        'Authorization': `Bearer ${token}`,
                    },
                });
                setProfile(response.data); // Populate profile with user data
            } catch (err) {
                setError('Failed to fetch profile data.');
                console.error(err);
            }
        };

        fetchProfile();
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setProfile({ ...profile, [name]: value });
    };

    const handleUpdate = async () => {
        const token = sessionStorage.getItem('jwtToken');
        try {
            const response = await axios.put('http://localhost:8080/api', profile, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            });
            if (response.data.token) {
                sessionStorage.setItem('token', response.data.token);
                setSuccessMessage('Profile has been updated successfully!');
                setTimeout(() => {
                    setSuccessMessage('');
                }, 3000);
            }
        } catch (err) {
            setError('Failed to update profile. Please try again.');
            console.error(err);
        }
    };

    return (
        <div className="profile-container">
            <h1>My Profile</h1>
            {error && <div className="error-message">{error}</div>}
            {successMessage && <div className="success-message">{successMessage}</div>}
            <div className="form-grid">
                <div className="form-group">
                    <label className="label">Username:</label>
                    <input 
                        type="text" 
                        name="username" 
                        value={profile.username} 
                        onChange={handleChange} 
                        readOnly 
                        className="input-field read-only" 
                    />
                </div>
                <div className="form-group">
                    <label className="label">Password:</label>
                    <input 
                        type="password" 
                        name="password" 
                        value={profile.password} 
                        onChange={handleChange} 
                        className="input-field" 
                    />
                </div>
                <div className="form-group">
                    <label className="label">First Name:</label>
                    <input type="text" name="firstName" value={profile.firstName} onChange={handleChange} className="input-field" />
                </div>
                <div className="form-group">
                    <label className="label">Last Name:</label>
                    <input type="text" name="lastName" value={profile.lastName} onChange={handleChange} className="input-field" />
                </div>
                <div className="form-group">
                    <label className="label">Email:</label>
                    <input type="email" name="email" value={profile.email} onChange={handleChange} className="input-field" />
                </div>
                <div className="form-group">
                    <label className="label">Gender:</label>
                    <input type="text" name="gender" value={profile.gender} onChange={handleChange} className="input-field" />
                </div>
                <div className="form-group">
                    <label className="label">Age:</label>
                    <input type="number" name="age" value={profile.age} onChange={handleChange} className="input-field" />
                </div>
                <div className="form-group">
                    <label className="label">Mobile Number:</label>
                    <input type="tel" name="mobileNumber" value={profile.mobileNumber} onChange={handleChange} className="input-field" />
                </div>
            </div>
            <div>
                <button onClick={handleUpdate} className="button">Update Profile</button>
            </div>
        </div>
    );
};

export default MyProfile;

