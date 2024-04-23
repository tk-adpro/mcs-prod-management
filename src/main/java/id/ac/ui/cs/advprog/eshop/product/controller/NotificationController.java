package id.ac.ui.cs.advprog.eshop.product.controller;

import id.ac.ui.cs.advprog.eshop.product.model.Notification;
import id.ac.ui.cs.advprog.eshop.product.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

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
        Notification notification = service.findById(notificationId);
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    @GetMapping("/getAllNotification")
    public ResponseEntity<List<Notification>> getAllNotification() {
        List<Notification> notifications = service.findAll();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

}
