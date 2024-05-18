package id.ac.ui.cs.advprog.eshop.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.eshop.product.model.Notification;
import id.ac.ui.cs.advprog.eshop.product.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Optional;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
public class NotificationControllerTest {
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())  // Add this to apply Spring Security configuration
                .build();    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void testGetAllNotification() throws Exception {
        List<Notification> notifications = Arrays.asList(new Notification(), new Notification());
        when(notificationService.findAll()).thenReturn(notifications);

        mockMvc.perform(get("/notification/admin/getAllNotification"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(notifications.size()));

        verify(notificationService, times(1)).findAll();
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void testGetNotificationById() throws Exception {
        String notificationId = "1";
        Notification notification = new Notification();
        notification.setNotificationId(notificationId);

        when(notificationService.findById(notificationId)).thenReturn(Optional.of(notification));

        mockMvc.perform(get("/notification/admin/getNotificationById/{notificationId}", notificationId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notificationId").value(notificationId));

        verify(notificationService, times(1)).findById(notificationId);
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void testCreateNotification() throws Exception {
        Notification notification = new Notification();
        notification.setNotificationId("Id1");

        when(notificationService.create(any(Notification.class))).thenReturn(notification);

        mockMvc.perform(post("/notification/admin/createNotification")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(notification)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notificationId").value(notification.getNotificationId()));

        verify(notificationService, times(1)).create(any(Notification.class));
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void testUpdateNotification() throws Exception {
        String notificationId = "1";
        Notification notification = new Notification();
        notification.setNotificationId(notificationId);

        doReturn(Optional.of(notification)).when(notificationService).findById(notificationId);
        doReturn(notification).when(notificationService).update(eq(notificationId), any(Notification.class));

        mockMvc.perform(put("/notification/admin/updateNotification/{notificationId}", notificationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(notification)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.notificationId").value(notification.getNotificationId()));

        verify(notificationService, times(1)).update(eq("1"), any(Notification.class));
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void testGetNotificationById_NotFound() throws Exception {
        String notificationId = "nonExistentId";

        when(notificationService.findById(notificationId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/notification/admin/getNotificationById/{notificationId}", notificationId))
                .andExpect(status().isNotFound());

        verify(notificationService, times(1)).findById(notificationId);
    }
    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void testUpdateNotification_NotFound() throws Exception {
        String notificationId = "nonExistentId";
        Notification notification = new Notification();
        notification.setNotificationId(notificationId);

        when(notificationService.findById(notificationId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/notification/admin/updateNotification/{notificationId}", notificationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(notification)))
                .andExpect(status().isNotFound());

        verify(notificationService, times(1)).findById(notificationId);
        verify(notificationService, never()).update(anyString(), any(Notification.class));
    }
    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void testDeleteNotification_Success() throws Exception {
        String notificationId = "validNotificationId";
        Notification notification = new Notification();
        notification.setNotificationId(notificationId);

        when(notificationService.findById(notificationId)).thenReturn(Optional.of(notification));
        when(notificationService.delete(notificationId)).thenReturn(true); // Simulate successful deletion

        mockMvc.perform(delete("/notification/admin/deleteNotification/{notificationId}", notificationId))
                .andExpect(status().isNoContent());

        verify(notificationService, times(1)).findById(notificationId);
        verify(notificationService, times(1)).delete(notificationId);
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void testDeleteNotification_NotFound() throws Exception {
        String notificationId = "nonExistentId";

        when(notificationService.findById(notificationId)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/notification/admin/deleteNotification/{notificationId}", notificationId))
                .andExpect(status().isNotFound());

        verify(notificationService, times(1)).findById(notificationId);
        verify(notificationService, never()).delete(notificationId);
    }

}