package id.ac.ui.cs.advprog.eshop.product.controller;

import id.ac.ui.cs.advprog.eshop.product.model.Product;
import id.ac.ui.cs.advprog.eshop.product.service.ProductService;
import id.ac.ui.cs.advprog.eshop.product.service.SortStrategy;
import id.ac.ui.cs.advprog.eshop.product.service.SortByDate;
import id.ac.ui.cs.advprog.eshop.product.service.SortByName;
import id.ac.ui.cs.advprog.eshop.product.service.SortByPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService service;

    @GetMapping("/public/getAllProducts")
    public CompletableFuture<ResponseEntity<List<Product>>> getAllProducts(@RequestParam(required = false) String sort) {
        SortStrategy strategy = null;
        if (sort != null) {
            switch (sort) {
                case "SortByName":
                    strategy = new SortByName();
                    break;
                case "SortByDate":
                    strategy = new SortByDate();
                    break;
                case "SortByPrice":
                    strategy = new SortByPrice();
                    break;
                default:
                    strategy = null;
                    break;
            }
        }

        return service.findAll(strategy)
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/public/getProductById/{productId}")
    public CompletableFuture<ResponseEntity<Product>> getProductById(@PathVariable String productId) {
        return service.findById(productId)
                .thenApply(product -> {
                    if (product != null) {
                        return ResponseEntity.ok(product);
                    } else {
                        return ResponseEntity.notFound().build();
                    }
                });
    }

    @PostMapping("/admin/createProduct")
    public ResponseEntity<HttpStatus> createProduct(@RequestBody Product product) {
        service.create(product);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PutMapping("/admin/updateProduct/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable String productId, @RequestBody Product product) {
        CompletableFuture<ResponseEntity<Product>> response = service.findById(productId)
                .thenApply(foundProduct -> {
                    if (foundProduct != null) {
                        service.update(product);
                        return ResponseEntity.ok(product);
                    } else {
                        return ResponseEntity.notFound().build();
                    }
                });

        try {
            return response.get(); // Block until the future completes
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @DeleteMapping("/admin/deleteProduct/{productId}")
    public CompletableFuture<ResponseEntity<?>> deleteProduct(@PathVariable String productId) {
        return service.findById(productId)
                .thenCompose(product -> {
                    if (product != null) {
                        logger.info("Product found: {}", product);
                        return service.delete(productId)
                                .thenApply(voidResult -> {
                                    return ResponseEntity.status(HttpStatus.ACCEPTED).body("Product with ID " + productId + " deleted successfully.");
                                });
                    } else {
                        return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
                    }
                });
    }

}

