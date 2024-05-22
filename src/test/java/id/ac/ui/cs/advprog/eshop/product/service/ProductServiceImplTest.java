package id.ac.ui.cs.advprog.eshop.product.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import id.ac.ui.cs.advprog.eshop.product.model.Product;
import id.ac.ui.cs.advprog.eshop.product.model.Notification;
import id.ac.ui.cs.advprog.eshop.product.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private NotificationService notificationService;
    
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("p1");
        product.setProductName("Product 1");
        product.setProductQuantity(1);
    }

    @Test
    void testCreateProduct() throws ExecutionException, InterruptedException {
        when(productRepository.save(product)).thenReturn(product);
        CompletableFuture<Product> createdProductFuture = productService.create(product);
        Product createdProduct = createdProductFuture.get();
        verify(productRepository).save(product);
        assertEquals(product.getProductId(), createdProduct.getProductId());
    }

    @Test
    void testFindAllProducts() throws ExecutionException, InterruptedException {
        Product product2 = new Product();
        product2.setProductId("p2");
        product2.setProductName("Product 2");
        when(productRepository.findAll()).thenReturn(List.of(product, product2));

        CompletableFuture<List<Product>> productsFuture = productService.findAll(null);
        List<Product> products = productsFuture.get();
        assertEquals(2, products.size());
        assertTrue(products.contains(product) && products.contains(product2));
    }


    @Test
    void testDeleteProductSuccess() throws ExecutionException, InterruptedException {
        String productId = "p1";
        doNothing().when(productRepository).deleteById(productId);
        CompletableFuture<Void> deleteResultFuture = productService.delete(productId);
        deleteResultFuture.get();
        verify(productRepository).deleteById(productId);
    }

    @Test
    void testDeleteProductFailure() {
        String productId = "p3";
        doThrow(new IllegalArgumentException("Product not found")).when(productRepository).deleteById(productId);
        assertThrows(IllegalArgumentException.class, () -> {
            CompletableFuture<Void> deleteResultFuture = productService.delete(productId);
            deleteResultFuture.get();  // This should throw an ExecutionException because of the IllegalArgumentException
        });
        verify(productRepository).deleteById(productId);  // Verify that deleteById was called
    }

    @Test
    void testFindProductByIdFound() throws ExecutionException, InterruptedException {
        Product expectedProduct = new Product(); // Example product setup
        expectedProduct.setProductId("existingId");
        when(productRepository.findById("existingId")).thenReturn(Optional.of(expectedProduct));

        CompletableFuture<Product> foundProductFuture = productService.findById("existingId");
        Product foundProduct = foundProductFuture.get(); // Wait for future to complete

        assertNotNull(foundProduct);
        assertEquals(expectedProduct.getProductId(), foundProduct.getProductId());
    }


    @Test
    void testFindProductByIdNotFound() throws ExecutionException, InterruptedException {
        when(productRepository.findById("unknown")).thenReturn(Optional.empty());

        CompletableFuture<Product> foundProductFuture = productService.findById("unknown");
        Product foundProduct = foundProductFuture.get();

        assertNull(foundProduct);
    }



    @Test
    void testUpdateProduct() {
        Product existingProduct = new Product();
        existingProduct.setProductId("12345");
        when(productRepository.findById("12345")).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        Product productToUpdate = new Product();
        productToUpdate.setProductId("12345");
        productToUpdate.setProductName("New Name");

        Product updatedProduct = productService.update(productToUpdate);

        verify(productRepository).save(existingProduct);
        assertEquals("12345", updatedProduct.getProductId());
        assertEquals("New Name", existingProduct.getProductName());
    }



    @Test
    void testUpdateProductWhenOutOfStock() {
        Product existingProduct = new Product();
        existingProduct.setProductId("12345");
        existingProduct.setProductQuantity(0); // Out of stock

        when(productRepository.findById("12345")).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        Notification notification = new Notification();
        when(notificationService.create(any(Notification.class))).thenReturn(notification);

        productService.update(existingProduct);

        verify(notificationService).create(any(Notification.class));
        verify(productRepository).save(existingProduct);
    }
    @Test
    void testUpdateProductWithZeroQuantity() {
        Product existingProduct = new Product();
        existingProduct.setProductId("12345");
        existingProduct.setProductQuantity(5);

        Product productToUpdate = new Product();
        productToUpdate.setProductId("12345");
        productToUpdate.setProductQuantity(0);

        when(productRepository.findById("12345")).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        Notification notification = new Notification();
        when(notificationService.create(any(Notification.class))).thenReturn(notification);

        productService.update(productToUpdate);

        verify(notificationService).create(any(Notification.class));
        verify(productRepository).save(existingProduct);
        assertEquals(0, existingProduct.getProductQuantity(), "Product quantity should be updated to zero");
    }
    @Test
    void testUpdateProductWithNonZeroQuantity() {
        Product existingProduct = new Product();
        existingProduct.setProductId("12345");
        existingProduct.setProductQuantity(5);

        Product productToUpdate = new Product();
        productToUpdate.setProductId("12345");
        productToUpdate.setProductQuantity(10);

        when(productRepository.findById("12345")).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        productService.update(productToUpdate);

        verify(notificationService, never()).create(any(Notification.class));
        verify(productRepository).save(existingProduct);
        assertEquals(10, existingProduct.getProductQuantity(), "Product quantity should be updated to 10");
    }

    @Test
    void testUpdateProductNotFound() {
        Product productToUpdate = new Product();
        productToUpdate.setProductId("nonExistingId");

        when(productRepository.findById("nonExistingId")).thenReturn(Optional.empty());

        Product result = productService.update(productToUpdate);

        verify(productRepository, never()).save(any(Product.class));
        assertNull(result, "Product should be null when not found in the repository");
    }


}
