import axios from "../axios";
import { useState, useEffect, createContext } from "react";

const AppContext = createContext({ //là context lưu trữ trạng thái toàn cục mặc định
  data: [],
  isError: "",
  cart: [],
  removeFromCart: (productId) => {},
  refreshData:() =>{},
  updateStockQuantity: (productId, newQuantity) =>{}
  
});

export const AppProvider = ({ children }) => {
  const [data, setData] = useState([]);
  const [isError, setIsError] = useState("");
  const [cart, setCart] = useState(JSON.parse(localStorage.getItem('cart')) || []);

  const removeFromCart = (productId) => {
    console.log("productID",productId)
    const updatedCart = cart.filter((item) => item.id !== productId);
    setCart(updatedCart);
    localStorage.setItem('cart', JSON.stringify(updatedCart));
  };

  const refreshData = async () => {
    try {
      const response = await axios.get("/products"); //Gửi yêu cầu GET đến API /products
      setData(response.data); //Cập nhật data nếu thành công
    } catch (error) {
      setIsError(error.message);
    }
  };

  useEffect(() => {
    refreshData(); //Gọi refreshData() một lần khi component được tải lần đầu tiên để lấy dữ liệu sản phẩm.
  }, []);

  useEffect(() => {
    localStorage.setItem('cart', JSON.stringify(cart));
  }, [cart]);
  
  return (
    <AppContext.Provider value={{ data, isError, removeFromCart, refreshData}}>
      {children}
    </AppContext.Provider>
  );
};

export default AppContext;