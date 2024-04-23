package com.example.ReactiveProgramming2.Utility;

import com.example.ReactiveProgramming2.Entity.Product;
import com.example.ReactiveProgramming2.Model.ProductModel;
import org.springframework.beans.BeanUtils;

public class AppUtils {
    public static Product modelToEntity(ProductModel productModel){
        Product product = new Product();
        BeanUtils.copyProperties(productModel,product);
        return product;
    }
    public static ProductModel entityToModel(Product product){
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(product,productModel);
        return productModel;
    }
}
