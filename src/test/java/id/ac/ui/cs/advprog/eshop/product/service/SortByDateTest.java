package id.ac.ui.cs.advprog.eshop.product.service;

import id.ac.ui.cs.advprog.eshop.product.model.Product;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

class SortByDateTest extends ProductSortTestSetup {

    @Test
    void testSortByDateHappyPath() {
//        productService.setSortStrategy(new SortByDate());
        List<Product> sortedProducts = productService.findAll(new SortByDate());
        assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0), sortedProducts.get(0).getProductAddedDate());
        assertEquals(LocalDateTime.of(2021, 1, 2, 0, 0), sortedProducts.get(1).getProductAddedDate());
        assertEquals(LocalDateTime.of(2021, 1, 3, 0, 0), sortedProducts.get(2).getProductAddedDate());
    }

    @Test
    void testSortByDateUnhappyPath() {
//        productService.setSortStrategy(new SortByDate());
        List<Product> sortedProducts = productService.findAll(new SortByDate());
        assertNotEquals(LocalDateTime.of(2021, 1, 2, 0, 0), sortedProducts.get(0).getProductAddedDate());
    }

}
