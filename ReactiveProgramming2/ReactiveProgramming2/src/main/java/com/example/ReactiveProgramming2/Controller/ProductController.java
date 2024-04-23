package com.example.ReactiveProgramming2.Controller;

import com.example.ReactiveProgramming2.Model.ProductModel;
import com.example.ReactiveProgramming2.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
//@RequestMapping("product")
public class ProductController {
    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
@GetMapping("getProducts")
    public Flux<ProductModel> getProducts(){
        return productService.getProducts();
    }
    @GetMapping("getProduct")
    public Mono<ProductModel> getProduct(String id){
        return productService.getProduct(id);
    }
    @GetMapping
    public Flux<ProductModel> getProductWithinRange(int max,int min){
        return productService.getProductsWithinRange(max,min);
    }
    @PostMapping
    public Mono<ProductModel> saveProduct(Mono<ProductModel> productModelMono) {
        return productService.saveProduct(productModelMono);
    }
    }


