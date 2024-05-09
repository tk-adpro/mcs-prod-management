package id.ac.ui.cs.advprog.eshop.product.controller;

import id.ac.ui.cs.advprog.eshop.product.model.Notification;
import id.ac.ui.cs.advprog.eshop.product.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @PostMapping("/admin/createNotification")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        Notification createdNotification = service.create(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNotification);
    }

    @GetMapping("/admin/getAllNotification")
    public ResponseEntity<List<Notification>> getAllNotification() {
        List<Notification> notifications = service.findAll();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/admin/getNotificationById/{notificationId}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable String notificationId) {
        return service.findById(notificationId)
                      .map(ResponseEntity::ok) // If found, return 200 OK with notification
                      .orElseGet(() -> ResponseEntity.notFound().build()); // If not found, return 404 Not Found
    }

    @PutMapping("/admin/updateNotification/{notificationId}")
    public ResponseEntity<Notification> updateNotification(@PathVariable String notificationId, @RequestBody Notification notification) {
        return service.findById(notificationId)
                    .map(n -> {
                        Notification updatedNotification = service.update(notificationId, notification);
                        return ResponseEntity.ok(updatedNotification);
                    }) 
                      .orElseGet(() -> ResponseEntity.notFound().build()); // If not found, return 404 Not Found
    }
}
