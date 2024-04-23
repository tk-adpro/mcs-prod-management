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
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    private Notification notification;
    private Product product;

    @BeforeEach
    void setUp() {
        notification = new Notification();
        product = new Product();
        product.setProductId("p1");
        product.setProductName("Product 1");
        notification.setProduct(product);
        notification.setNotificationId("n1");
        notification.setRead(false);
    }
    @Test
    void testCreateNotification() {
        doReturn(notification).when(notificationRepository).create(notification);
        Notification createdNotification = notificationService.create(notification);
        verify(notificationRepository, times(1)).create(notification);
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
        Iterator<Notification> notificationIterator = Arrays.asList(notification, notification2).iterator();
        doReturn(notificationIterator).when(notificationRepository).findAll();

        Iterator<Notification> results = notificationService.findAll();
        assertTrue(results.hasNext());
        assertEquals(notification.getNotificationId(), results.next().getNotificationId());
        assertEquals(notification2.getNotificationId(), results.next().getNotificationId());
    }
    @Test
    void testFindNotificationByIdFound() {
        doReturn(notification).when(notificationRepository).findById("n1");
        Notification foundNotification = notificationService.findById("n1");
        assertNotNull(foundNotification);
        assertEquals("n1", foundNotification.getNotificationId());
    }

    @Test
    void testFindNotificationByIdNotFound() {
        doReturn(null).when(notificationRepository).findById("unknown");
        assertNull(notificationService.findById("unknown"));
    }
    @Test
    void testUpdateNotification() {
        Notification updatedNotification = new Notification();
        updatedNotification.setNotificationId("n1");
        updatedNotification.setRead(true);

        doReturn(updatedNotification).when(notificationRepository).update("n1", updatedNotification);
        Notification result = notificationService.update("n1", updatedNotification);
        verify(notificationRepository, times(1)).update("n1", updatedNotification);
        assertEquals("n1", result.getNotificationId());
        assertTrue(result.isRead());
    }

    @Test
    void testUpdateNotificationWithNonExistingId() {
        Notification updatedNotification = new Notification();
        updatedNotification.setNotificationId("n3");
        updatedNotification.setRead(true);

        when(notificationRepository.update("n3", updatedNotification)).thenThrow(new NoSuchElementException());
        assertThrows(NoSuchElementException.class, () -> notificationService.update("n3", updatedNotification));
        verify(notificationRepository, times(1)).update("n3", updatedNotification);
    }
}
