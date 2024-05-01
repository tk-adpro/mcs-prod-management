package id.ac.ui.cs.advprog.eshop.product.repository;

import id.ac.ui.cs.advprog.eshop.product.model.Notification;
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
class NotificationRepositoryTest {

    @Mock
    private NotificationRepository notificationRepository;

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
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);
        Notification createdNotification = notificationRepository.save(notification);
        assertNotNull(createdNotification);
        assertEquals("notif123", createdNotification.getNotificationId());
    }

    @Test
    void testCreateNotification_UnhappyPath_NullNotification() {
        when(notificationRepository.save(null)).thenThrow(new IllegalArgumentException("Notification cannot be null"));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> notificationRepository.save(null));
        assertEquals("Notification cannot be null", exception.getMessage());
    }

    @Test
    void testFindAll_HappyPath() {
        when(notificationRepository.findAll()).thenReturn(List.of(notification));
        List<Notification> notifications = notificationRepository.findAll();
        assertFalse(notifications.isEmpty());
        assertEquals(notification, notifications.get(0));
    }

    @Test
    void testFindAll_UnhappyPath_EmptyRepository() {
        when(notificationRepository.findAll()).thenReturn(List.of());
        List<Notification> notifications = notificationRepository.findAll();
        assertTrue(notifications.isEmpty());
    }

    @Test
    void testFindById_HappyPath() {
        when(notificationRepository.findById(notification.getNotificationId())).thenReturn(Optional.of(notification));
        Optional<Notification> foundNotification = notificationRepository.findById(notification.getNotificationId());
        assertTrue(foundNotification.isPresent());
        assertEquals("notif123", foundNotification.get().getNotificationId());
    }

    @Test
    void testFindById_UnhappyPath_NotificationNotFound() {
        when(notificationRepository.findById("nonexistent-id")).thenReturn(Optional.empty());
        Optional<Notification> foundNotification = notificationRepository.findById("nonexistent-id");
        assertFalse(foundNotification.isPresent());
    }

    @Test
    void testUpdate_HappyPath() {
        Notification updatedNotification = new Notification();
        updatedNotification.setNotificationId(notification.getNotificationId());
        updatedNotification.setRead(true);
        when(notificationRepository.save(updatedNotification)).thenReturn(updatedNotification);

        Notification result = notificationRepository.save(updatedNotification);
        assertNotNull(result);
        assertTrue(result.isRead());
    }

    @Test
    void testUpdate_UnhappyPath_NotificationNotFound() {
        Notification updatedNotification = new Notification();
        updatedNotification.setNotificationId("nonexistent-id");
        updatedNotification.setRead(true);
        when(notificationRepository.findById("nonexistent-id")).thenReturn(Optional.empty());

        Optional<Notification> result = notificationRepository.findById("nonexistent-id");
        assertFalse(result.isPresent());
    }
}
