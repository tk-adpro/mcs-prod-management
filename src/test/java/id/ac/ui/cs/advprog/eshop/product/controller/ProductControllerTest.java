package id.ac.ui.cs.advprog.eshop.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.eshop.product.model.Product;
import id.ac.ui.cs.advprog.eshop.product.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @Test
    public void testGetAllProducts() throws Exception {
        // Mocking the service response
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productService.findAll()).thenReturn(products);

        mockMvc.perform(get("/product/getAllProducts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(products.size()));

        verify(productService, times(1)).findAll();
    }

    @Test
    public void testGetProductById() throws Exception {
        String productId = "1";
        Product product = new Product();
        product.setProductId(productId);

        when(productService.findById(productId)).thenReturn(product);

        mockMvc.perform(get("/product/getProductById/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(productId));

        verify(productService, times(1)).findById(productId);
    }

    @Test
    public void testCreateProduct() throws Exception {
        Product product = new Product();
        product.setProductName("Test Product");

        when(productService.create(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/product/createProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productName").value(product.getProductName()));

        verify(productService, times(1)).create(any(Product.class));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        String productId = "1";
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Updated Product");

        when(productService.update(any(Product.class))).thenReturn(product);

        mockMvc.perform(put("/product/updateProduct/{productId}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productName").value(product.getProductName()));

        verify(productService, times(1)).update(any(Product.class));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        String productId = "1";

        when(productService.delete(productId)).thenReturn(true);

        mockMvc.perform(delete("/product/deleteProduct/{productId}", productId))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).delete(productId);
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }
}
