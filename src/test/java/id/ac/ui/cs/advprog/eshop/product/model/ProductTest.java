package id.ac.ui.cs.advprog.eshop.product.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Base64;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;
    private LocalDateTime addedDate;
    private String base64Image;

    @BeforeEach
    void setUp() {
        this.product = new Product();
        this.product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setProductName("Pajangan Doraemon");
        this.product.setProductDescription("High-quality shampoo for daily use");
        this.product.setProductPrice(49.99);
        this.product.setProductDiscount(5.00);
        this.product.setProductQuantity(100);
        this.addedDate = LocalDateTime.now();
        this.product.setProductAddedDate(addedDate);

        // Assuming the image is represented as a base64 string for simplicity
        this.base64Image = Base64.getEncoder().encodeToString("dummyimagecontent".getBytes());
        this.product.setProductImage(base64Image);
    }

    @Test
    void testGetProductId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.product.getProductId());
    }

    @Test
    void testGetProductName() {
        assertEquals("Sampo Cap Bambang", this.product.getProductName());
    }

    @Test
    void testGetProductDescription() {
        assertEquals("High-quality shampoo for daily use", this.product.getProductDescription());
    }

    @Test
    void testGetProductPrice() {
        assertEquals(49.99, this.product.getProductPrice());
    }

    @Test
    void testGetProductDiscount() {
        assertEquals(5.00, this.product.getProductDiscount());
    }

    @Test
    void testGetProductQuantity() {
        assertEquals(100, this.product.getProductQuantity());
    }

    @Test
    void testGetProductAddedDate() {
        assertEquals(addedDate, this.product.getProductAddedDate());
    }

    @Test
    void testGetProductImage() {
        assertEquals(base64Image, this.product.getProductImage());
    }
}
