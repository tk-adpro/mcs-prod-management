package id.ac.ui.cs.advprog.eshop.product.repository;

import id.ac.ui.cs.advprog.eshop.product.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

}
