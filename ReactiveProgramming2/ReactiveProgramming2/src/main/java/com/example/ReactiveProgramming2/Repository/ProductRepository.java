package com.example.ReactiveProgramming2.Repository;

import com.example.ReactiveProgramming2.Entity.Product;
import com.example.ReactiveProgramming2.Model.ProductModel;
import org.springframework.data.domain.Range;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product,String> {

    Flux<ProductModel> findByPriceBetween(Range<Integer> closed);
}
