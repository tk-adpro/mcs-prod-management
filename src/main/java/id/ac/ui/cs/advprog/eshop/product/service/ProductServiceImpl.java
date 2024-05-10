package id.ac.ui.cs.advprog.eshop.product.service;

import id.ac.ui.cs.advprog.eshop.product.model.Notification;
import id.ac.ui.cs.advprog.eshop.product.model.Product;
import id.ac.ui.cs.advprog.eshop.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private NotificationService notificationService;

    private SortStrategy sortStrategy;

    public void setSortStrategy(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAll(SortStrategy sorting) {
        List<Product> allProducts = productRepository.findAll();
        if (sorting != null) {
            allProducts = sorting.sort(allProducts);
        }
        return allProducts;
    }

    @Override
    public boolean delete(String productId) {
        productRepository.deleteById(productId);
        return true;
    }

    @Override
    public Optional<Product> findById(String productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product update(Product product) {
        if (product.getProductQuantity() == 0) {
            Notification notification = new Notification();
            notification.setProductId(product.getProductId());
            notification.setRead(false);
            notificationService.create(notification);
        }
        return productRepository.save(product); 
    }

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
