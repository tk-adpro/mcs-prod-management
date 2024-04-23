package id.ac.ui.cs.advprog.eshop.product.service;

import id.ac.ui.cs.advprog.eshop.product.model.Product;
import id.ac.ui.cs.advprog.eshop.product.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    private SortStrategy sortStrategy;

    // Method untuk mengatur strategi sortir
    public void setSortStrategy(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }
    public SortStrategy getSortStrategy() {
        return this.sortStrategy;
    }

    @Override
    public Product create(Product product) {
        return productRepository.create(product);
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProducts = new ArrayList<>();
        productIterator.forEachRemaining(allProducts::add);

        allProducts.forEach(p -> System.out.println(p.getProductName() + " - " + p.getProductPrice()));

        if (sortStrategy != null) {
            allProducts = sortStrategy.sort(allProducts);
        }
        allProducts.forEach(p -> System.out.println(p.getProductName() + " - " + p.getProductPrice()));

        return allProducts;
    }

    @Override
    public boolean delete(String productId) {
        return productRepository.delete(productId);
    }

    @Override
    public Product findById(String productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product update(Product product) {
        return productRepository.update(product.getProductId(), product);
    }

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
