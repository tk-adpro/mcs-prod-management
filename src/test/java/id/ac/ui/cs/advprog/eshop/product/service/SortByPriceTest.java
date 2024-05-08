package id.ac.ui.cs.advprog.eshop.product.service;

import id.ac.ui.cs.advprog.eshop.product.model.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class SortByPriceTest extends ProductSortTestSetup {

    @Test
    void testSortByPriceHappyPath() {
//        productService.setSortStrategy(new SortByPrice());
        List<Product> sortedProducts = productService.findAll(new SortByPrice());
        System.out.println(sortedProducts.get(0).getProductPrice());
        System.out.println(sortedProducts.get(1).getProductPrice());
        System.out.println(sortedProducts.get(2).getProductPrice());

        assertEquals(0.75, sortedProducts.get(0).getProductPrice());
        assertEquals(1.25, sortedProducts.get(1).getProductPrice());
        assertEquals(1.50, sortedProducts.get(2).getProductPrice());
    }

    @Test
    void testSortByPriceUnhappyPath() {
//        productService.setSortStrategy(new SortByPrice());
        List<Product> sortedProducts = productService.findAll(new SortByPrice());
        assertNotEquals(1.25, sortedProducts.get(0).getProductPrice());
    }
}
