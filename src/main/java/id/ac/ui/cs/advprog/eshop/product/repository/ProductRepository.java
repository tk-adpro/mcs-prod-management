package id.ac.ui.cs.advprog.eshop.product.repository;

import id.ac.ui.cs.advprog.eshop.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product findByProductId(String productId);
}
