package id.ac.ui.cs.advprog.eshop.product.service;

import id.ac.ui.cs.advprog.eshop.product.model.Product;
import java.util.List;
import java.util.Optional;
public interface ProductService {
    void setSortStrategy(SortStrategy sortStrategy);
    Product create(Product product);
    List<Product> findAll(SortStrategy sortStrategy);
    Optional<Product> findById(String productId);
    boolean delete(String productId);
    Product update(Product product);
}
