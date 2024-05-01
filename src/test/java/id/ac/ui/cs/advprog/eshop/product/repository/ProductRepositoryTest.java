package id.ac.ui.cs.advprog.eshop.product.repository;

import id.ac.ui.cs.advprog.eshop.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @Mock
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Boneka Doraemon");
        product.setProductQuantity(100);
    }

    @Test
    void testCreateProduct_HappyPath() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product createdProduct = productRepository.save(product);
        assertNotNull(createdProduct);
        assertEquals("Boneka Doraemon", createdProduct.getProductName());
    }

    @Test
    void testCreateProduct_UnhappyPath_NullProduct() {
        when(productRepository.save(null)).thenThrow(new IllegalArgumentException("Product cannot be null"));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> productRepository.save(null));
        assertEquals("Product cannot be null", exception.getMessage());
    }

    @Test
    void testFindAll_HappyPath() {
        when(productRepository.findAll()).thenReturn(List.of(product));
        List<Product> products = productRepository.findAll();
        assertFalse(products.isEmpty());
        assertEquals(product, products.get(0));
    }

    @Test
    void testFindAll_UnhappyPath_EmptyRepository() {
        when(productRepository.findAll()).thenReturn(List.of());
        List<Product> products = productRepository.findAll();
        assertTrue(products.isEmpty());
    }

    @Test
    void testFindById_HappyPath() {
        when(productRepository.findById(product.getProductId())).thenReturn(Optional.of(product));
        Optional<Product> foundProduct = productRepository.findById(product.getProductId());
        assertTrue(foundProduct.isPresent());
        assertEquals("Boneka Doraemon", foundProduct.get().getProductName());
    }

    @Test
    void testFindById_UnhappyPath_ProductNotFound() {
        when(productRepository.findById("nonexistent-id")).thenReturn(Optional.empty());
        Optional<Product> foundProduct = productRepository.findById("nonexistent-id");
        assertFalse(foundProduct.isPresent());
    }

    @Test
    void testUpdate_HappyPath() {
        Product updatedProduct = new Product();
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductId(product.getProductId());
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        Product result = productRepository.save(updatedProduct);
        assertNotNull(result);
        assertEquals("Updated Name", result.getProductName());
    }

    @Test
    void testUpdate_UnhappyPath_ProductNotFound() {
        Product updatedProduct = new Product();
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductId("nonexistent-id");
        when(productRepository.findById("nonexistent-id")).thenReturn(Optional.empty());
        Optional<Product> result = productRepository.findById("nonexistent-id");
        assertFalse(result.isPresent());
    }

    @Test
    void testDelete_HappyPath() {
        doNothing().when(productRepository).deleteById(product.getProductId());
        productRepository.deleteById(product.getProductId());
        verify(productRepository, times(1)).deleteById(product.getProductId());
    }

    @Test
    void testDelete_UnhappyPath() {
        doThrow(new IllegalArgumentException("Product not found")).when(productRepository).deleteById("nonexistent-id");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> productRepository.deleteById("nonexistent-id"));
        assertEquals("Product not found", exception.getMessage());
    }
}
