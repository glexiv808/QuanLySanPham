package com.BTL.QuanLySanPham.controllers;

//import com.BTL.QuanLySanPham.model.Product;
import com.BTL.QuanLySanPham.model.Product;
import com.BTL.QuanLySanPham.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
    @Autowired //Autowired sẽ tìm kiếm một bean có kiểu dữ liệu phù hợp trong Application Context và tiêm vào biến hoặc phương thức tương ứng
    private ProductServices service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){


        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){ // @PathVariable dùng để trích xuất các giá trị từ URL và gán chúng vào các tham số của phương thức trong controller
       Product product = service.getProductsById(id);

       if(product != null)
        return new ResponseEntity<>( service.getProductsById(id), HttpStatus.OK);
       else
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile){ //RequestBody sẽ match data mà mình gửi và cho nó vào product
       try {
           Product product1 = service.addProduct(product, imageFile);
           return new ResponseEntity<>(product1, HttpStatus.CREATED);
       }
       catch (Exception e){
           return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
       }

    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){

        Product product = service.getProductsById(productId);
        byte[] imageFile = product.getImageData();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product,
                                                @RequestPart MultipartFile imageFile){
        Product product1 = null;
        try{
            product1 = service.updateProduct(id, product, imageFile);
        }catch (IOException e){
            return new ResponseEntity<>("Cant Updated", HttpStatus.BAD_REQUEST);
        }
        if (product1 != null)
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("Cant Updated", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product = service.getProductsById(id);
        if(product != null){
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Cant Deleted", HttpStatus.NOT_FOUND);

    }
}



//
//    @DeleteMapping("/products/{prodId}")
//    public void deleteProduct(@PathVariable int prodId){
//        service.deleteProduct(prodId);
//    }

