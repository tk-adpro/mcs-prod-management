package id.ac.ui.cs.advprog.eshop.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.eshop.product.model.Notification;
import id.ac.ui.cs.advprog.eshop.product.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @Test
    public void testGetAllNotification() throws Exception {
        List<Notification> notifications = Arrays.asList(new Notification(), new Notification());
        when(notificationService.findAll()).thenReturn(notifications);

        mockMvc.perform(get("/notification/getAllNotification"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(notifications.size()));

        verify(notificationService, times(1)).findAll();
    }

    @Test
    public void testGetNotificationById() throws Exception {
        String notificationId = "1";
        Notification notification = new Notification();
        notification.setNotificationId(notificationId);

        when(notificationService.findById(notificationId)).thenReturn(Optional.of(notification));

        mockMvc.perform(get("/notification/getNotificationById/{notificationId}", notificationId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notificationId").value(notificationId));

        verify(notificationService, times(1)).findById(notificationId);
    }

    @Test
    public void testCreateNotification() throws Exception {
        Notification notification = new Notification();
        notification.setNotificationId("Id1");

        when(notificationService.create(any(Notification.class))).thenReturn(notification);

        mockMvc.perform(post("/notification/createNotification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(notification)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notificationId").value(notification.getNotificationId()));

        verify(notificationService, times(1)).create(any(Notification.class));
    }

    @Test
    public void testUpdateNotification() throws Exception {
        String notificationId = "1";
        Notification notification = new Notification();
        notification.setNotificationId(notificationId);

        doReturn(Optional.of(notification)).when(notificationService).findById(notificationId);
        doReturn(notification).when(notificationService).update(eq(notificationId), any(Notification.class));

        mockMvc.perform(put("/notification/updateNotification/{notificationId}", notificationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(notification)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notificationId").value(notification.getNotificationId()));

        verify(notificationService, times(1)).update(eq("1"), any(Notification.class));
    }

    @Test
    public void testGetNotificationById_NotFound() throws Exception {
        String notificationId = "nonExistentId";

        when(notificationService.findById(notificationId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/notification/getNotificationById/{notificationId}", notificationId))
                .andExpect(status().isNotFound());

        verify(notificationService, times(1)).findById(notificationId);
    }
    @Test
    public void testUpdateNotification_NotFound() throws Exception {
        String notificationId = "nonExistentId";
        Notification notification = new Notification();
        notification.setNotificationId(notificationId);

        when(notificationService.findById(notificationId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/notification/updateNotification/{notificationId}", notificationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(notification)))
                .andExpect(status().isNotFound());

        verify(notificationService, times(1)).findById(notificationId);
        verify(notificationService, never()).update(anyString(), any(Notification.class));
    }

}