package id.ac.ui.cs.advprog.eshop.product.service;

import id.ac.ui.cs.advprog.eshop.product.model.Product;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


import java.util.List;

class SortByNameTest extends ProductSortTestSetup {

    @Test
    void testSortByNameHappyPath() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Product>> futureProducts = productService.findAll(new SortByName());
        List<Product> sortedProducts = futureProducts.get();
        assertEquals("Amigo", sortedProducts.get(0).getProductName());
        assertEquals("Bonita", sortedProducts.get(1).getProductName());
        assertEquals("Omago", sortedProducts.get(2).getProductName());
    }

    @Test
    void testSortByNameUnhappyPath() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Product>> futureProducts = productService.findAll(new SortByName());
        List<Product> sortedProducts = futureProducts.get();
        assertNotEquals("Orange", sortedProducts.get(0).getProductName());
    }
}
