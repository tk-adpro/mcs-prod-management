package id.ac.ui.cs.advprog.eshop.product.controller;

import id.ac.ui.cs.advprog.eshop.product.model.Notification;
import id.ac.ui.cs.advprog.eshop.product.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

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
                      .map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/admin/updateNotification/{notificationId}")
    public ResponseEntity<Notification> updateNotification(@PathVariable String notificationId, @RequestBody Notification notification) {
        return service.findById(notificationId)
                    .map(n -> {
                        Notification updatedNotification = service.update(notificationId, notification);
                        return ResponseEntity.ok(updatedNotification);
                    }) 
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/admin/deleteNotification/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable String notificationId) {
        return service.findById(notificationId)
                      .map(notification -> {
                          service.delete(notificationId);
                          return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                      })
                      .orElse(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}
