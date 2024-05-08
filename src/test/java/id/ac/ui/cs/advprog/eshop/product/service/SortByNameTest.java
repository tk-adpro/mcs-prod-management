package id.ac.ui.cs.advprog.eshop.product.service;

import id.ac.ui.cs.advprog.eshop.product.model.Product;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.List;

class SortByNameTest extends ProductSortTestSetup {

    @Test
    void testSortByNameHappyPath() {
//        productService.setSortStrategy(new SortByName());
        List<Product> sortedProducts = productService.findAll(new SortByName());


        assertEquals("Amigo", sortedProducts.get(0).getProductName());
        assertEquals("Bonita", sortedProducts.get(1).getProductName());
        assertEquals("Omago", sortedProducts.get(2).getProductName());
    }

    @Test
    void testSortByNameUnhappyPath() {
//        productService.setSortStrategy(new SortByName());
        List<Product> sortedProducts = productService.findAll(new SortByName());
        assertNotEquals("Orange", sortedProducts.get(0).getProductName());
    }
}
