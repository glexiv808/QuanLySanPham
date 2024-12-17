import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const LoginForm = ({ setIsAuthenticated }) => { //để cập nhật trạng thái đăng nhập trong component cha
    const [username, setUsername] = useState(''); // khai báo rỗng để lưu thông tin người dùng
    const [password, setPassword] = useState('');
    const navigate = useNavigate(); // điều hướng người dùng sau khi đăng nhập thành công.

    const handleSubmit = (e) => {
        e.preventDefault(); //Chặn hành vi mặc định của form (tải lại trang).
        if (username === 'admin' && password === '123') { //check tài khoản
            localStorage.setItem('isAuthenticated', 'true'); //Lưu trạng thái đăng nhập vào localStorage để duy trì ngay cả khi làm mới trình duyệt.
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
