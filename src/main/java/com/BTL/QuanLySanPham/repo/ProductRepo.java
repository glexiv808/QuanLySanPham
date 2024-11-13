package com.BTL.QuanLySanPham.repo;

import com.BTL.QuanLySanPham.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

}
