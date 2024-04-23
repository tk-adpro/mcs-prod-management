package id.ac.ui.cs.advprog.eshop.product.repository;

import id.ac.ui.cs.advprog.eshop.product.model.Notification;
import id.ac.ui.cs.advprog.eshop.product.model.Product;
import id.ac.ui.cs.advprog.eshop.product.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationRepositoryTest {

    @InjectMocks
    private NotificationRepository mockNotificationRepository;

    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Boneka Doraemon");
        product.setProductQuantity(100);
        notification.setNotificationId("notif123");
        notification.setProduct(product);
        notification.setRead(false);
    }

    @Test
    void testCreateNotification_HappyPath() {
        Notification createdNotification = mockNotificationRepository.create(notification);
        assertNotNull(createdNotification);
        assertEquals("notif123", createdNotification.getNotificationId());
    }

    @Test
    void testCreateNotification_UnhappyPath_NullNotification() {
        Notification createdNotification = mockNotificationRepository.create(null);
        assertNull(createdNotification, "Notification creation should fail when trying to add null.");
    }

    @Test
    void testFindAll_HappyPath() {
        mockNotificationRepository.create(notification);
        Iterator<Notification> notifications = mockNotificationRepository.findAll();
        assertTrue(notifications.hasNext());
        assertEquals(notification, notifications.next());
    }

    @Test
    void testFindAll_UnhappyPath_EmptyRepository() {
        Iterator<Notification> notifications = mockNotificationRepository.findAll();
        assertFalse(notifications.hasNext(), "Notification iterator should have no next element when the repository is empty.");
    }

    @Test
    void testFindById_HappyPath() {
        mockNotificationRepository.create(notification);
        Notification foundNotification = mockNotificationRepository.findById(notification.getNotificationId());
        assertNotNull(foundNotification);
        assertEquals("notif123", foundNotification.getNotificationId());
    }

    @Test
    void testFindById_UnhappyPath_NotificationNotFound() {
        Notification foundNotification = mockNotificationRepository.findById("nonexistent-id");
        assertNull(foundNotification, "Should return null for a non-existent notification ID.");
    }

    @Test
    void testUpdate_HappyPath() {
        mockNotificationRepository.create(notification);
        Notification updatedNotification = new Notification();
        updatedNotification.setNotificationId(notification.getNotificationId());
        updatedNotification.setRead(true); // Change read status to true

        Notification result = mockNotificationRepository.update(notification.getNotificationId(), updatedNotification);
        assertNotNull(result);
        assertTrue(result.isRead());
    }

    @Test
    void testUpdate_UnhappyPath_NotificationNotFound() {
        Notification updatedNotification = new Notification();
        updatedNotification.setNotificationId("nonexistent-id");
        updatedNotification.setRead(true);

        Notification result = mockNotificationRepository.update("nonexistent-id", updatedNotification);
        assertNull(result, "Should return null when trying to update a non-existent notification.");
    }
}
