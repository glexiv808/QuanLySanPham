import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const LoginForm = ({ setIsAuthenticated }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        if (username === 'admin' && password === '123') {
            localStorage.setItem('isAuthenticated', 'true');
            setIsAuthenticated(true); // Cập nhật trạng thái vào App
            navigate('/home');
        } else {
            alert('Tên đăng nhập hoặc mật khẩu không đúng!');
        }
    };

    return (
        <div style={{ maxWidth: '400px', margin: '300px auto', padding: '20px', border: '1px solid #ccc', borderRadius: '8px' }}>
            <h2 style={{ textAlign: 'center', color:'white' }}>Đăng Nhập</h2>
            <form onSubmit={handleSubmit}>
                <div style={{ marginBottom: '15px' }}>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        style={{ width: '100%', padding: '10px', border: '1px solid #ccc', borderRadius: '4px' }}
                        placeholder="Nhập tên đăng nhập"
                        required
                    />
                </div>
                <div style={{ marginBottom: '15px' }}>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        style={{ width: '100%', padding: '10px', border: '1px solid #ccc', borderRadius: '4px' }}
                        placeholder="Nhập mật khẩu"
                        required
                    />
                </div>
                <button
                    type="submit"
                    style={{
                        width: '100%',
                        padding: '10px',
                        backgroundColor: '#007bff',
                        color: '#fff',
                        border: 'none',
                        borderRadius: '4px',
                        cursor: 'pointer',
                        
                    }}
                >
                    Đăng Nhập
                </button>
            </form>
        </div>
    );
};

export default LoginForm;
