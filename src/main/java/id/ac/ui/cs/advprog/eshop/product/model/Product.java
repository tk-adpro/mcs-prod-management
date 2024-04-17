package id.ac.ui.cs.advprog.eshop.product.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Product {
    private String productId;
    private String productName;
    private String productDescription;
    private double productPrice;
    private double productDiscount;
    private int productQuantity;
    private LocalDateTime productAddedDate;
    private String productImage; // Base64 encoded image


}
