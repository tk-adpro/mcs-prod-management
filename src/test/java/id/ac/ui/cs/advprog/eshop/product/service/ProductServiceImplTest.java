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
    void testCreateProduct() {
        when(productRepository.save(product)).thenReturn(product);
        Product createdProduct = productService.create(product);
        verify(productRepository).save(product);
        assertEquals(product.getProductId(), createdProduct.getProductId());
    }

    @Test
    void testFindAllProducts() {
        Product product2 = new Product();
        product2.setProductId("p2");
        product2.setProductName("Product 2");
        when(productRepository.findAll()).thenReturn(List.of(product, product2));

        List<Product> products = productService.findAll(null);
        assertEquals(2, products.size());
        assertTrue(products.contains(product) && products.contains(product2));
    }

    @Test
    void testDeleteProductSuccess() {
        doNothing().when(productRepository).deleteById(product.getProductId());
        productService.delete(product.getProductId());
        verify(productRepository).deleteById(product.getProductId());
    }

    @Test
    void testDeleteProductFailure() {
        doThrow(new IllegalArgumentException("Product not found")).when(productRepository).deleteById("p3");
        assertThrows(IllegalArgumentException.class, () -> productService.delete("p3"));
        verify(productRepository).deleteById("p3");
    }

    @Test
    void testFindProductByIdFound() {
        when(productRepository.findById(product.getProductId())).thenReturn(Optional.of(product));
        Product foundProduct = productService.findById(product.getProductId()).orElse(null);
        assertNotNull(foundProduct);
        assertEquals(product.getProductId(), foundProduct.getProductId());
    }

    @Test
    void testFindProductByIdNotFound() {
        when(productRepository.findById("unknown")).thenReturn(Optional.empty());
        Optional<Product> foundProduct = productService.findById("unknown");
        assertFalse(foundProduct.isPresent());
    }

    @Test
    void testUpdateProduct() {
        when(productRepository.save(product)).thenReturn(product);
        Product updatedProduct = productService.update(product);
        verify(productRepository).save(product);
        assertEquals(product.getProductId(), updatedProduct.getProductId());
    }

    @Test
    void testUpdateProductWhenOutOfStock() {
        product.setProductQuantity(0);
        when(notificationService.create(any())).thenReturn(new Notification());
        when(productRepository.save(product)).thenReturn(product);

        productService.update(product);

        verify(notificationService).create(any());
        verify(productRepository).save(product);
    }

}
