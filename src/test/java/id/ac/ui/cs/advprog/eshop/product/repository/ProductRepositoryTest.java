package id.ac.ui.cs.advprog.eshop.product.repository;

import id.ac.ui.cs.advprog.eshop.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    private ProductRepository mockProductRepository;


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
        Product createdProduct = mockProductRepository.create(product);
        assertNotNull(createdProduct);
        assertEquals("Boneka Doraemon", createdProduct.getProductName());
    }

    @Test
    void testCreateProduct_UnhappyPath_NullProduct() {
        Product createdProduct = mockProductRepository.create(null);
        assertNull(createdProduct, "Product creation should fail when trying to add null.");
    }

    @Test
    void testFindAll_HappyPath() {
        Product createdProduct = mockProductRepository.create(product);
        Iterator<Product> products = mockProductRepository.findAll();
        assertTrue(products.hasNext());
        assertEquals(product, products.next());
    }

    @Test
    void testFindAll_UnhappyPath_EmptyRepository() {
        Iterator<Product> products = mockProductRepository.findAll();
        assertFalse(products.hasNext(), "Product iterator should have no next element when the repository is empty.");
    }

    @Test
    void testFindById_HappyPath() {
        Product product1 = mockProductRepository.create(product);
        Product foundProduct = mockProductRepository.findById(product.getProductId());
        assertNotNull(foundProduct);
        assertEquals("Boneka Doraemon", foundProduct.getProductName());
    }

    @Test
    void testFindById_UnhappyPath_ProductNotFound() {
        Product foundProduct = mockProductRepository.findById("nonexistent-id");
        assertNull(foundProduct, "Should return null for a non-existent product ID.");
    }


    @Test
    void testUpdate_HappyPath() {
        mockProductRepository.create(product);
        Product updatedProduct = new Product();
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductId(product.getProductId());
        Product result = mockProductRepository.update(product.getProductId(), updatedProduct);
        System.out.println(result);
        assertNotNull(result);
        assertEquals("Updated Name", result.getProductName());
    }

    @Test
    void testUpdate_UnhappyPath_ProductNotFound() {
        mockProductRepository.create(product);
        Product updatedProduct = new Product();
        updatedProduct.setProductId("nonexistent-id");
        updatedProduct.setProductName("Updated Name");

        Product result = mockProductRepository.update("nonexistent-id", updatedProduct);
        assertNull(result, "Should return null when trying to update a non-existent product.");
    }


    @Test
    void testDelete_HappyPath() {
        mockProductRepository.create(product);
        assertTrue(mockProductRepository.delete(product.getProductId()));
    }

    @Test
    void testDelete_UnhappyPath() {
        mockProductRepository.create(product);
        assertFalse(mockProductRepository.delete("nonexistent-id"));
    }
}
