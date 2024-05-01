package id.ac.ui.cs.advprog.eshop.product.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Getter
@Setter
@Entity
@Table(name = "Product")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
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


}
