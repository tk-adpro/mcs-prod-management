package id.ac.ui.cs.advprog.eshop.product.service;

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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
        Product product = new Product();
        product.setProductId("p1");
        product.setProductName("Product 1");
        notification.setProduct(product);
        notification.setNotificationId("n1");
        notification.setRead(false);
    }

    @Test
    void testCreateNotification() {
        when(notificationRepository.save(notification)).thenReturn(notification);
        Notification createdNotification = notificationService.create(notification);
        verify(notificationRepository).save(notification);
        assertEquals(notification.getNotificationId(), createdNotification.getNotificationId());
    }

    @Test
    void testFindAllNotifications() {
        Notification notification2 = new Notification();
        notification2.setNotificationId("n2");
        Product product2 = new Product();
        product2.setProductId("p2");
        product2.setProductName("Product 2");
        notification2.setProduct(product2);
        notification2.setRead(false);
        when(notificationRepository.findAll()).thenReturn(Arrays.asList(notification, notification2));

        List<Notification> results = notificationService.findAll();

        assertEquals(2, results.size());
        assertEquals(notification.getNotificationId(), results.get(0).getNotificationId());
        assertEquals(notification2.getNotificationId(), results.get(1).getNotificationId());
    }

    @Test
    void testFindNotificationByIdFound() {
        when(notificationRepository.findById("n1")).thenReturn(Optional.of(notification));
        Notification foundNotification = notificationService.findById("n1").orElse(null);
        assertNotNull(foundNotification);
        assertEquals("n1", foundNotification.getNotificationId());
    }

    @Test
    void testFindNotificationByIdNotFound() {
        when(notificationRepository.findById("unknown")).thenReturn(Optional.empty());
        Optional<Notification> result = notificationService.findById("unknown");
        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateNotification() {
        Notification updatedNotification = new Notification();
        updatedNotification.setNotificationId("n1");
        updatedNotification.setRead(true);
        when(notificationRepository.save(updatedNotification)).thenReturn(updatedNotification);

        Notification result = notificationService.update("n1", updatedNotification);
        verify(notificationRepository).save(updatedNotification);
        assertEquals("n1", result.getNotificationId());
        assertTrue(result.isRead());
    }

    @Test
    void testUpdateNotificationWithNonExistingId() {
        Notification updatedNotification = new Notification();
        updatedNotification.setNotificationId("n3");
        updatedNotification.setRead(true);
        when(notificationRepository.save(updatedNotification)).thenReturn(updatedNotification);
        
        Notification result = notificationService.update("n3", updatedNotification);
        assertNotNull(result);
        verify(notificationRepository).save(updatedNotification);
    }
    @Test
    void testDeleteNotification_HappyPath() {
        String notificationId = "n1";
        doNothing().when(notificationRepository).deleteById(notificationId);
        boolean result = notificationService.delete(notificationId);
        verify(notificationRepository).deleteById(notificationId);
        assertTrue(result);
    }
    @Test
    void testDeleteNotification_UnhappyPath() {
        String notificationId = "invalidId";
        doThrow(new RuntimeException("Database error")).when(notificationRepository).deleteById(notificationId);
        Exception exception = assertThrows(RuntimeException.class, () -> notificationService.delete(notificationId));
        assertEquals("Database error", exception.getMessage());
        verify(notificationRepository).deleteById(notificationId);
    }
    
}