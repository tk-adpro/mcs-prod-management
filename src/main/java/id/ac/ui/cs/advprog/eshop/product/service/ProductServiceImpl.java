package id.ac.ui.cs.advprog.eshop.product.service;

import id.ac.ui.cs.advprog.eshop.product.model.Notification;
import id.ac.ui.cs.advprog.eshop.product.model.Product;
import id.ac.ui.cs.advprog.eshop.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;
import java.util.Optional;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    private ProductRepository productRepository;
    private NotificationService notificationService;

    public ProductServiceImpl(ProductRepository productRepository, NotificationService notificationService){
        this.productRepository = productRepository;
        this.notificationService = notificationService;
    }

    @Async
    @Override
    public CompletableFuture<Product> create(Product product) {
        return CompletableFuture.supplyAsync(() -> productRepository.save(product));
    }

    public CompletableFuture<List<Product>> findAll(SortStrategy sorting) {
        return CompletableFuture.supplyAsync(() -> {
            List<Product> allProducts = productRepository.findAll();
            if (sorting != null) {
                allProducts = sorting.sort(allProducts);
            }
            return allProducts;
        });
    }

    @Async
    @Override
    public CompletableFuture<Void> delete(String productId) {
        productRepository.deleteById(productId);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Product> findById(String productId) {
        return CompletableFuture.completedFuture(productRepository.findById(productId).orElse(null));
    }


    @Override
    public Product update(Product incomingProduct) {
        Optional<Product> existingProductOpt = productRepository.findById(incomingProduct.getProductId());

        if (existingProductOpt.isPresent()) {
            Product existingProduct = existingProductOpt.get();

            // Update fields from incoming product to existing product
            existingProduct.setProductName(incomingProduct.getProductName());
            existingProduct.setProductDescription(incomingProduct.getProductDescription());
            existingProduct.setProductPrice(incomingProduct.getProductPrice());
            existingProduct.setProductDiscount(incomingProduct.getProductDiscount());
            existingProduct.setProductQuantity(incomingProduct.getProductQuantity());
            existingProduct.setProductAddedDate(incomingProduct.getProductAddedDate());
            existingProduct.setProductImage(incomingProduct.getProductImage());

            // Check for notification condition
            if (incomingProduct.getProductQuantity() == 0) {
                Notification notification = new Notification();
                notification.setProduct(existingProduct); // Refer to the existing product
                notification.setRead(false);
                notificationService.create(notification);
            }

            // Save the updated product
            return productRepository.save(existingProduct);
        }

        // Return null or handle the case where the product does not exist
        return null;
    }
//    @Override
//    public Product update(Product product) {
//        if (product.getProductQuantity() == 0) {
//            Notification notification = new Notification();
//            notification.setProduct(product);
//            notification.setRead(false);
//            notificationService.create(notification);
//        }
//        return productRepository.save(product);
//    }

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
