package id.ac.ui.cs.advprog.eshop.product.repository;

import id.ac.ui.cs.advprog.eshop.product.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        if(product == null){
            return null;
        }
        if (product.getProductId() == null || product.getProductId().isEmpty()) {
            product.setProductId(java.util.UUID.randomUUID().toString());
        }
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String productId) {
        if (productId == null || productId.isEmpty()) {
            return null;
        }
        for (Product product : productData) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public Product update(String id, Product updatedProduct) {
        if (id == null || id.isEmpty() || updatedProduct == null) {
            return null;
        }
        for (int i = 0; i < productData.size(); i++) {
            if (productData.get(i).getProductId().equals(id)) {
                updatedProduct.setProductId(id);  
                productData.set(i, updatedProduct);
                return updatedProduct;
            }
        }
        return null;  
    }

    public boolean delete(String productId) {
        return productData.removeIf(product -> product.getProductId().equals(productId));
    }
}
