package id.ac.ui.cs.advprog.eshop.product.controller;

import id.ac.ui.cs.advprog.eshop.product.model.Notification;
import id.ac.ui.cs.advprog.eshop.product.model.Product;
import id.ac.ui.cs.advprog.eshop.product.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @PutMapping("/updateNotification/{notificationId}")
    public ResponseEntity<Notification> updateNotification(@PathVariable String notificationId, @RequestBody Notification notification) {
        Notification updatedNotification = service.update(notificationId, notification);
        return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
    }

    @PostMapping("/createNotification")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        Notification createdNotification = service.create(notification);
        return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
    }

    @GetMapping("/getNotificationById/{notificationId}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable String notificationId) {
        Optional<Notification> notification = service.findById(notificationId);
        if(notification.isPresent()){
            return ResponseEntity.ok(notification.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllNotification")
    public ResponseEntity<List<Notification>> getAllNotification() {
        List<Notification> notifications = service.findAll();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

}
