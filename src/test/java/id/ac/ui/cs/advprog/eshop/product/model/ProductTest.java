package id.ac.ui.cs.advprog.eshop.product.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Base64;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;

class ProductTest {

    private Product product;
    private LocalDateTime addedDate;
    private String base64Image;

    @BeforeEach
    void setUp() {
        this.product = new Product();
        this.product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setProductName("Pajangan Doraemon");
        this.product.setProductDescription("High-quality decoration for aesthetics use");
        this.product.setProductPrice(49.99);
        this.product.setProductDiscount(5.00);
        this.product.setProductQuantity(100);
        this.addedDate = LocalDateTime.now();
        this.product.setProductAddedDate(addedDate);

        this.base64Image = Base64.getEncoder().encodeToString("dummyimagecontent".getBytes());
        this.product.setProductImage(base64Image);
    }

    @Test
    void testGetProductId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.product.getProductId());
    }

    @Test
    void testGetProductName() {
        assertEquals("Pajangan Doraemon", this.product.getProductName());
    }

    @Test
    void testGetProductDescription() {
        assertEquals("High-quality decoration for aesthetics use", this.product.getProductDescription());
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
    @Test
    void testEnsureProductId() {
        Product newProduct = new Product();
        assertNull(newProduct.getProductId(), "ProductId should initially be null");
        newProduct.ensureProductId();
        assertNotNull(newProduct.getProductId(), "ProductId should not be null after ensureProductId is called");
        assertTrue(newProduct.getProductId().matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"),
                "ProductId should be a valid UUID");
    }

    @Test
    void testEnsureProductId_alreadySet() {
        Product productWithId = new Product();
        String preSetId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        productWithId.setProductId(preSetId);
        productWithId.ensureProductId();
        assertEquals(preSetId, productWithId.getProductId(), "ProductId should not change if already set");
    }
    @Test
    void testEqualsAndHashCode() {
        Product product1 = new Product();
        product1.setProductId("1");
        product1.setProductName("Product A");
        product1.setProductDescription("Description A");
        product1.setProductPrice(100.0);
        product1.setProductDiscount(10.0);
        product1.setProductQuantity(50);
        product1.setProductAddedDate(LocalDateTime.now());
        product1.setProductImage(Base64.getEncoder().encodeToString("ImageA".getBytes()));

        Product product2 = new Product();
        product2.setProductId("1");
        product2.setProductName("Product B"); // Different name
        product2.setProductDescription("Description B"); // Different description
        product2.setProductPrice(200.0); // Different price
        product2.setProductDiscount(20.0); // Different discount
        product2.setProductQuantity(100); // Different quantity
        product2.setProductAddedDate(LocalDateTime.now().plusDays(1)); // Different date
        product2.setProductImage(Base64.getEncoder().encodeToString("ImageB".getBytes()));

        assertEquals(product1, product2, "Products with the same ID should be considered equal even if other attributes differ");

        assertEquals(product1.hashCode(), product2.hashCode(), "Hash codes should be equal for equal objects");

        Product product3 = new Product();
        product3.setProductId("2");
        product3.setProductName("Product A");

        assertNotEquals(product1, product3, "Products with different IDs should not be considered equal");

        assertNotEquals(product1.hashCode(), product3.hashCode(), "Hash codes should not be equal for non-equal objects");

        assertEquals(product1, product1, "A product should be equal to itself");

        assertNotEquals(product1, null, "A product should not be equal to null");
        assertNotEquals(product1, new Object(), "A product should not be equal to an object of a different type");
    }
    @Test
    void testCanEqual() {
        Product product = new Product();
        Object otherObject = new Object();

        assertTrue(product.canEqual(new Product()), "Product should be able to equal another product");
        assertFalse(product.canEqual(otherObject), "Product should not be able to equal a generic Object");
    }
    @Test
    void testSetNotifications() {
        Notification notification1 = new Notification();
        notification1.setNotificationId("notif1");

        Notification notification2 = new Notification();
        notification2.setNotificationId("notif2");

        Set<Notification> notifications = new HashSet<>();
        notifications.add(notification1);
        notifications.add(notification2);

        product.setNotifications(notifications);

        assertEquals(notifications, product.getNotifications(), "Notifications should be correctly set and retrieved");
    }

}
