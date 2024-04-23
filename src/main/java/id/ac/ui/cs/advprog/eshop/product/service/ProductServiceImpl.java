package id.ac.ui.cs.advprog.eshop.product.service;

import id.ac.ui.cs.advprog.eshop.product.model.Notification;
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

    @Autowired
    private NotificationService notificationService;

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


        if (sortStrategy != null) {
            allProducts = sortStrategy.sort(allProducts);}
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
        if(product.getProductQuantity() == 0){
            Notification notification = new Notification();
            notification.setNotificationId(java.util.UUID.randomUUID().toString());
            notification.setProduct(product);
            notification.setRead(false);
            notificationService.create(notification);
        }
        return productRepository.update(product.getProductId(), product);
    }

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
