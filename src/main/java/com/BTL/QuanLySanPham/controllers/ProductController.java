package com.BTL.QuanLySanPham.controllers;

import com.BTL.QuanLySanPham.model.Product;
import com.BTL.QuanLySanPham.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductServices service;

    @RequestMapping("/products")
    public List<Product> getProducts(){
        return service.getProducts();
    }
}
