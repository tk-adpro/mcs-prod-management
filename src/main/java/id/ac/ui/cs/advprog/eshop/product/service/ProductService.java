package id.ac.ui.cs.advprog.eshop.product.service;

import id.ac.ui.cs.advprog.eshop.product.model.Product;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductService {
    CompletableFuture<Product> create(Product product);
    CompletableFuture<List<Product>> findAll(SortStrategy sortStrategy);
    CompletableFuture<Product> findById(String productId);
    CompletableFuture<Void> delete(String productId) ;
    Product update(Product product);
}
