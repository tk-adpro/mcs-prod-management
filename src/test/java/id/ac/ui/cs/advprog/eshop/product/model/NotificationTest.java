package id.ac.ui.cs.advprog.eshop.product.model;

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
        this.notification.setNotificationId("4842948f-83e2-4abe-8c77-25a42984ecbd");
        this.notification.setProduct(this.product);
        this.notification.setRead(false);
    }

    @Test
    void testGetNotificationId() {
        assertEquals("4842948f-83e2-4abe-8c77-25a42984ecbd", notification.getNotificationId());
    }

    @Test
    void testGetProduct() {
        assertEquals(product.getProductId(), notification.getProduct().getProductId());
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
    @Test
    void testInitializeUUID_NotSet() {
        Notification newNotification = new Notification();
        newNotification.setProduct(new Product());  // Assuming a product needs to be set
        newNotification.setRead(false);

        assertNull(newNotification.getNotificationId(), "NotificationId should initially be null");

        // Simulate the @PrePersist trigger
        newNotification.initializeUUID();

        assertNotNull(newNotification.getNotificationId(), "NotificationId should not be null after initializeUUID");
        assertTrue(newNotification.getNotificationId().matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"),
                "NotificationId should be a valid UUID");
    }
    @Test
    void initializeUUID_WhenIdIsNull_ShouldGenerateNewUUID() {
        Notification notification = new Notification();
        // Do not set the notificationId, it should be null by default
        assertNull(notification.getNotificationId(), "NotificationId should initially be null");

        notification.initializeUUID(); // This should generate a new UUID

        assertNotNull(notification.getNotificationId(), "NotificationId should not be null after initializeUUID");
        assertTrue(notification.getNotificationId().matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"),
                "NotificationId should be a valid UUID");
    }

    @Test
    void initializeUUID_WhenIdIsNotNull_ShouldNotChangeExistingId() {
        String existingId = "existing-uuid-1234";
        Notification notification = new Notification();
        notification.setNotificationId(existingId); // Explicitly setting an ID

        notification.initializeUUID(); // This should not change the existing ID

        assertEquals(existingId, notification.getNotificationId(), "NotificationId should remain unchanged after initializeUUID");
    }
}