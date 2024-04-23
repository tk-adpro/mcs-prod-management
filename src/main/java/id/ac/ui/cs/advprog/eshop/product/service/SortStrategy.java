package id.ac.ui.cs.advprog.eshop.product.service;

import id.ac.ui.cs.advprog.eshop.product.model.Product;
import java.util.List;

public interface SortStrategy {
    List<Product> sort(List<Product> products);
}
