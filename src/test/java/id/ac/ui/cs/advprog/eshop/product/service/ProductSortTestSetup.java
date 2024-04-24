package id.ac.ui.cs.advprog.eshop.product.service;

import id.ac.ui.cs.advprog.eshop.product.model.Product;
import id.ac.ui.cs.advprog.eshop.product.repository.ProductRepository;

import java.time.LocalDateTime;


import org.junit.jupiter.api.BeforeEach;

public abstract class ProductSortTestSetup {
    protected ProductServiceImpl productService;
    protected ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        productRepository = new ProductRepository();
        productService = new ProductServiceImpl();
        productService.setProductRepository(productRepository);

        Product product2 = new Product();
        product2.setProductId("2");
        product2.setProductName("Omago");
        product2.setProductDescription("Kiyowo Omago");
        product2.setProductPrice(1.5);
        product2.setProductQuantity(150);
        product2.setProductAddedDate(LocalDateTime.of(2021, 1, 2, 0, 0));
        product2.setProductImage("");

        Product product3 = new Product();
        product3.setProductId("3");
        product3.setProductName("Bonita");
        product3.setProductDescription("Cute Bonita");
        product3.setProductPrice(0.75);
        product3.setProductQuantity(200);
        product3.setProductAddedDate(LocalDateTime.of(2021, 1, 3, 0, 0));
        product3.setProductImage("");

        Product product1 = new Product();
        product1.setProductId("1");
        product1.setProductName("Amigo");
        product1.setProductDescription("Cute Amigo");
        product1.setProductPrice(1.25);
        product1.setProductQuantity(100);
        product1.setProductAddedDate(LocalDateTime.of(2021, 1, 1, 0, 0));
        product1.setProductImage("");

        productService.create(product1);
        productService.create(product2);
        productService.create(product3);

    }
}
