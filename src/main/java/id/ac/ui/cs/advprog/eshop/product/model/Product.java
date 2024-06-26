package id.ac.ui.cs.advprog.eshop.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import java.util.Objects;
import jakarta.persistence.OneToMany;
import java.util.Set;
import jakarta.persistence.CascadeType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @Column(name = "product_id", updatable = false, nullable = false)
    private String productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_price")
    private double productPrice;

    @Column(name = "product_discount")
    private double productDiscount;

    @Column(name = "product_quantity")
    private int productQuantity;

    @Column(name = "product_added_date")
    private LocalDateTime productAddedDate;
    
    @Lob
    @Column(name = "product_image")
    private String productImage; // Base64 encoded image

    @PrePersist
    public void ensureProductId() {
        if (productId == null) {
            productId = UUID.randomUUID().toString();
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product pr = (Product) o;
        return Objects.equals(productId, pr.productId);
    }

    public boolean canEqual(Object obj) {
        return obj instanceof Product;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Notification> notifications;

}
