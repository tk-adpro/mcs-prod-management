package id.ac.ui.cs.advprog.eshop.product.service;

import id.ac.ui.cs.advprog.eshop.product.model.Notification;
import id.ac.ui.cs.advprog.eshop.product.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {



    private NotificationRepository notificationRepository;
    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notification create(Notification notification) {
        return notificationRepository.save(notification); 
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Optional<Notification> findById(String id) {
        return notificationRepository.findById(id); 
    }

    @Override
    public Notification update(String id, Notification updatedNotification) {
        return notificationRepository.save(updatedNotification); 
    }
    @Override
    public boolean delete(String productId) {
        notificationRepository.deleteById(productId);
        return true;
    }
}
