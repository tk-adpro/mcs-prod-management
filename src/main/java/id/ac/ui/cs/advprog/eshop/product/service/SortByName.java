package id.ac.ui.cs.advprog.eshop.product.service;

import id.ac.ui.cs.advprog.eshop.product.model.Product;
import java.util.Comparator;
import java.util.List;

public class SortByName implements SortStrategy {
    @Override
    public List<Product> sort(List<Product> products) {
        return products.stream()
                .sorted(Comparator.comparing(product -> product.getProductName().toLowerCase()))
                .toList();
    }
}
