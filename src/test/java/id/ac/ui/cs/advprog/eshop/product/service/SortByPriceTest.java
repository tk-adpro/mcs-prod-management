package id.ac.ui.cs.advprog.eshop.product.service;

import id.ac.ui.cs.advprog.eshop.product.model.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import java.util.List;

class SortByPriceTest extends ProductSortTestSetup {

    @Test
    void testSortByPriceHappyPath() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Product>> futureProducts = productService.findAll(new SortByPrice());
        List<Product> sortedProducts = futureProducts.get();

        assertEquals(0.75, sortedProducts.get(0).getProductPrice(), 0.01);
        assertEquals(1.25, sortedProducts.get(1).getProductPrice(), 0.01);
        assertEquals(1.50, sortedProducts.get(2).getProductPrice(), 0.01);
    }

    @Test
    void testSortByPriceUnhappyPath() throws ExecutionException, InterruptedException {
        CompletableFuture<List<Product>> futureProducts = productService.findAll(new SortByPrice());
        List<Product> sortedProducts = futureProducts.get();
        assertNotEquals(1.25, sortedProducts.get(0).getProductPrice(), 0.01);
    }
}
