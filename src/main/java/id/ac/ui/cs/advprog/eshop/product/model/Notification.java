package id.ac.ui.cs.advprog.eshop.product.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Notification {
    private String notificationId;
    private Product product;
    private boolean isRead;
}
