package com.BTL.QuanLySanPham.services;

import com.BTL.QuanLySanPham.model.Product;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
@Service
public class ProductServices {

    List<Product> products = Arrays.asList(
            new Product(101,"Iphone", 500000),
            new Product(102,"Ipad", 500000));
    public List<Product> getProducts() {
        return products;
    }
}
