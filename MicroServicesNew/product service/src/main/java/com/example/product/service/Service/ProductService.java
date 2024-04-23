package com.example.product.service.Service;

import com.example.product.service.Module.Product;
import com.example.product.service.Module.ProductRequest;
import com.example.product.service.Module.ProductResponse;
import com.example.product.service.Repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;
@Autowired
    public ProductService(ProductRepository productRepository) {

    this.productRepository = productRepository;
    }

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("PRODUCT IS SAVED {}" + product.getId());
    }

    public List<ProductResponse> getAllProducts() {
  List<Product> products = productRepository.findAll();
 return  products.stream().map(product -> (ProductResponse) mapToProductResponse(product)).toList();

    }

    private Object mapToProductResponse(Product product) {
    ProductResponse productResponse = ProductResponse.builder()
            .id(product.getId())
            .description(product.getDescription())
            .name(product.getName())
            .price(product.getPrice())
            .build();
    return productResponse;
    }
}
