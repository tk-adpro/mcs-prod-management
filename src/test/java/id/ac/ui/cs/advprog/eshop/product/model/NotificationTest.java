package id.ac.ui.cs.advprog.eshop.product.model;

import id.ac.ui.cs.advprog.eshop.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    private Notification notification;
    private Product product;

    @BeforeEach
    void setUp() {
        // Set up a Product instance as part of the Notification
        this.product = new Product();
        this.product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setProductName("Pajangan Doraemon");
        this.product.setProductDescription("High-quality decoration for aesthetics use");
        this.product.setProductPrice(49.99);
        this.product.setProductDiscount(5.00);
        this.product.setProductQuantity(100);

        this.notification = new Notification();
        this.notification.setNotificationId("notif12345");
        this.notification.setProduct(this.product);
        this.notification.setRead(false);
    }

    @Test
    void testGetNotificationId() {
        assertEquals("notif12345", notification.getNotificationId());
    }

    @Test
    void testGetProduct() {
        assertEquals(product, notification.getProduct());
    }

    @Test
    void testIsRead() {
        assertFalse(notification.isRead());
    }

    @Test
    void testSetRead() {
        notification.setRead(true);
        assertTrue(notification.isRead());
    }
}
