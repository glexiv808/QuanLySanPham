import "./App.css";
import React, { useState, useEffect } from "react";
import LoginForm from './components/LoginForm';
import Home from "./components/Home";
import Navbar from "./components/Navbar";
import AddProduct from "./components/AddProduct";
import Product from "./components/Product";
import { BrowserRouter, Routes, Route, Navigate} from "react-router-dom";
import { AppProvider } from "./Context/Context";
import UpdateProduct from "./components/UpdateProduct";
import PrivateRoute from "./components/PrivateRoute";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import 'bootstrap/dist/css/bootstrap.min.css';


function App() {
  const [cart, setCart] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState("");

  const [isAuthenticated, setIsAuthenticated] = useState(null);

  useEffect(() => {
    const storedAuthStatus = localStorage.getItem('isAuthenticated');

      setIsAuthenticated(storedAuthStatus === 'true');

}, []);

const logout = () => {
  localStorage.removeItem('isAuthenticated'); // Xóa trạng thái trong localStorage
  setIsAuthenticated(false);                 // Cập nhật trạng thái
};

  const handleCategorySelect = (category) => {
    setSelectedCategory(category);
    console.log("Selected category:", category);
  };
  const addToCart = (product) => {
    const existingProduct = cart.find((item) => item.id === product.id);
    if (existingProduct) {
      setCart(
        cart.map((item) =>
          item.id === product.id
            ? { ...item, quantity: item.quantity + 1 }
            : item
        )
      );
    } else {
      setCart([...cart, { ...product, quantity: 1 }]);
    }
  };

  if (isAuthenticated === null) {
    return <div>Loading...</div>;
}
  return (
    <AppProvider>
      <BrowserRouter>
        <Navbar onSelectCategory={handleCategorySelect}/>     
        <Routes>
          <Route path="/login" element={<LoginForm setIsAuthenticated={setIsAuthenticated} />} />
          <Route path="/home"
            element={
              <PrivateRoute isAuthenticated={isAuthenticated}>
                <Home logout={logout} addToCart={addToCart} selectedCategory={selectedCategory}/>
              </PrivateRoute>
            }/>
          <Route path="/" element={<Navigate to="/login" replace />} />
          <Route path="/add_product" element={
            <PrivateRoute isAuthenticated={isAuthenticated}>
                <AddProduct />
            </PrivateRoute> 
            } />
          <Route path="/product" element={
            <PrivateRoute isAuthenticated={isAuthenticated}>
              <Product />
            </PrivateRoute>
            }/>
          <Route path="/product/:id" element={
            <PrivateRoute isAuthenticated={isAuthenticated}>
              <Product  />
            </PrivateRoute>
            } />
          <Route path="/product/update/:id" element={
            <PrivateRoute isAuthenticated={isAuthenticated}>
              <UpdateProduct />
            </PrivateRoute>
            } />
        </Routes>
      </BrowserRouter>
    </AppProvider>
  );
  
}

export default App;