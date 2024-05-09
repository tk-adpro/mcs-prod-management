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

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(required = false) String sort) {
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
            }
        }
        List<Product> products = service.findAll(strategy);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/getProductById/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable String productId) {
        return service.findById(productId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/admin/createProduct")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = service.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable String productId, @RequestBody Product product) {
        return service.findById(productId)
                .map(p -> ResponseEntity.ok(service.update(product)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/admin/deleteProduct/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String productId) {
        return service.delete(productId) ? 
            ResponseEntity.noContent().build() :
            ResponseEntity.notFound().build();
    }
}
