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

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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
        doReturn(product).when(productRepository).create(product);
        Product createdProduct = productService.create(product);
        verify(productRepository, times(1)).create(product);
        assertEquals(product.getProductId(), createdProduct.getProductId());
    }

    @Test
    void testFindAllProducts() {
        Product product2 = new Product();
        product2.setProductId("p2");
        product2.setProductName("Product 2");

        Iterator<Product> productIterator = Arrays.asList(product, product2).iterator();
        doReturn(productIterator).when(productRepository).findAll();
        
        List<Product> products = productService.findAll();
        assertEquals(2, products.size());
        assertTrue(products.contains(product) && products.contains(product2));
    }

    @Test
    void testDeleteProductSuccess() {
        String productId = "p1";
        doReturn(true).when(productRepository).delete(productId);
        assertTrue(productService.delete(productId));
        verify(productRepository, times(1)).delete(productId);
    }

    @Test
    void testDeleteProductFailure() {
        String productId = "p3";
        doReturn(false).when(productRepository).delete(productId);
        assertFalse(productService.delete(productId));
        verify(productRepository, times(1)).delete(productId);
    }

    @Test
    void testFindProductByIdFound() {
        String productId = "p1";
        doReturn(product).when(productRepository).findById(productId);
        Product foundProduct = productService.findById(productId);
        assertNotNull(foundProduct);
        assertEquals(productId, foundProduct.getProductId());
    }

    @Test
    void testFindProductByIdNotFound() {
        String productId = "unknown";
        doReturn(null).when(productRepository).findById(productId);
        assertNull(productService.findById(productId));
    }

    @Test
    void testUpdateProduct() {
        doReturn(product).when(productRepository).update(anyString(), any(Product.class));
        Product updatedProduct = productService.update(product);
        verify(productRepository, times(1)).update(product.getProductId(), product);
        assertEquals(product.getProductId(), updatedProduct.getProductId());
    }

    @Test
    void testUpdateProductWithNonExistingId() {
        when(productRepository.update(anyString(), any(Product.class))).thenThrow(new NoSuchElementException());
        assertThrows(NoSuchElementException.class, () -> productService.update(product));
        verify(productRepository, times(1)).update(product.getProductId(), product);
    }

    @Test
    void testUpdateProductWhenOutOfStock() {
        product.setProductQuantity(0);

        when(notificationService.create(any())).thenReturn(new Notification());

        doReturn(product).when(productRepository).update(anyString(), any(Product.class));

        productService.update(product);

        verify(notificationService, times(1)).create(any());
        verify(productRepository, times(1)).update(product.getProductId(), product);
    }

}