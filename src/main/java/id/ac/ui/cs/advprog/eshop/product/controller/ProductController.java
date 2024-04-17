package id.ac.ui.cs.advprog.eshop.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    @GetMapping("/")
    public ResponseEntity<String> getHello() {
        return ResponseEntity.ok("Hello product!");
    };
}