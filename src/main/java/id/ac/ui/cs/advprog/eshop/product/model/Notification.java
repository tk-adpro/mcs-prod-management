package id.ac.ui.cs.advprog.eshop.product.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;


@Getter
@Setter
@Entity
@Table(name = "Notification")
public class Notification {
    @Id
    @Column(name = "notification_id", updatable = false, nullable = false)
    private String notificationId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonProperty("isRead") 
    @Column(name = "is_read")
    private boolean isRead;

    @PrePersist
    public void initializeUUID() {
        if (notificationId == null) {
            notificationId = UUID.randomUUID().toString();
        }
    }
}
