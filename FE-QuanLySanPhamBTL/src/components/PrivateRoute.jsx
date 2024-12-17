import React from "react";
import { Navigate } from "react-router-dom";

const ProtectedRoute = ({ children }) => { // kiểm tra trạng thái đăng nhập của người dùng trước khi cho phép truy cập các trang được bảo vệ
  const isAuthenticated = localStorage.getItem("isAuthenticated"); //Giá trị này thường được lưu khi người dùng đăng nhập thành công (true)
  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }
  return children;
};

export default ProtectedRoute;