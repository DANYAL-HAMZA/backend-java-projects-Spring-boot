package com.example.ReactiveProgramming2.Service;

import com.example.ReactiveProgramming2.Entity.Product;
import com.example.ReactiveProgramming2.Model.ProductModel;
import com.example.ReactiveProgramming2.Repository.ProductRepository;
import com.example.ReactiveProgramming2.Utility.AppUtils;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.desktop.AppHiddenListener;

@Service
public class ProductService {
    private ProductRepository productRepository;
    public Flux<ProductModel> getProducts(){
        return productRepository.findAll()
                .map(AppUtils::entityToModel);
    }
    public Mono<ProductModel> getProduct(String id){
        return  productRepository.findById(id)
                .map(AppUtils::entityToModel);
    }
    public Flux<ProductModel> getProductsWithinRange(int max, int min){
        return productRepository.findByPriceBetween(Range.closed(min,max));
    }
    public Mono<ProductModel> saveProduct(Mono<ProductModel> monoProductModel){
        return monoProductModel.map(AppUtils::modelToEntity)
                .flatMap(productRepository::save)
                .map(AppUtils::entityToModel);
    }
    public Mono<ProductModel> updateProduct(Mono<ProductModel> productModelMono, String id){
        return productRepository.findById(id)
                .flatMap(p->productModelMono.map(AppUtils::modelToEntity)
                .doOnNext(e->e.setId(id)))
                .flatMap(productRepository::save)
                .map(AppUtils::entityToModel);
    }
    public Mono<Void> deleteProduct(String id){
        return productRepository.deleteById(id);

    }
}
