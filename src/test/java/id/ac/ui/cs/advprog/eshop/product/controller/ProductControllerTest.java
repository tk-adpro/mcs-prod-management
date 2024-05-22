package id.ac.ui.cs.advprog.eshop.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.eshop.product.model.Product;
import id.ac.ui.cs.advprog.eshop.product.service.ProductService;
import id.ac.ui.cs.advprog.eshop.product.service.SortByDate;
import id.ac.ui.cs.advprog.eshop.product.service.SortByName;
import id.ac.ui.cs.advprog.eshop.product.service.SortByPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;
import java.util.concurrent.ExecutionException;
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ProductService productService;
    @Mock
    private ProductService productServices;
    @InjectMocks
    private ProductController productController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())  // Add this to apply Spring Security configuration
                .build();
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    void testGetAllProducts() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        CompletableFuture<List<Product>> futureProducts = CompletableFuture.completedFuture(products);
        when(productService.findAll(null)).thenReturn(futureProducts); // Return CompletableFuture directly

        mockMvc.perform(get("/product/public/getAllProducts").param("sort", ""))
                .andExpect(status().isOk());

        verify(productService, times(1)).findAll(null);
    }
    @Test
    @WithMockUser(username="user", roles={"USER"})
    void testGetAllProducts_InvalidSort() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        CompletableFuture<List<Product>> futureProducts = CompletableFuture.completedFuture(products);

        when(productService.findAll(null)).thenReturn(futureProducts);  // No sorting strategy applied

        mockMvc.perform(get("/product/public/getAllProducts").param("sort", "invalidSort"))
                .andExpect(status().isOk());

        verify(productService, times(1)).findAll(null);  // Verify that no sorting strategy was applied
    }
    @Test
    @WithMockUser(username="user", roles={"USER"})
    void testGetProductById() throws Exception {
        String productId = "123";
        Product product = new Product();
        product.setProductId(productId);

        CompletableFuture<Product> futureProduct = CompletableFuture.completedFuture(product);
        when(productService.findById(productId)).thenReturn(futureProduct);

        mockMvc.perform(get("/product/public/getProductById/{productId}", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService, times(1)).findById(productId);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateProduct() throws Exception {
        String productId = "123";
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Test Product");
        product.setProductPrice(100.0);
        product.setProductQuantity(10);

        CompletableFuture<Product> futureProduct = CompletableFuture.completedFuture(product);
        when(productService.create(any(Product.class))).thenReturn(futureProduct);

        MvcResult result = mockMvc.perform(post("/product/admin/createProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product)))
                .andReturn();  // Capture the response

        // Manually check the status
        assertEquals(202, result.getResponse().getStatus());

        verify(productService, times(1)).create(any(Product.class));
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateProductWithException() throws Exception {
        String productId = "1";
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Updated Product");

        CompletableFuture<Product> future = new CompletableFuture<>();
        future.completeExceptionally(new ExecutionException("Error during async operation", new Throwable()));

        when(productService.findById(productId)).thenReturn(future);

        MvcResult result = mockMvc.perform(put("/product/admin/updateProduct/{productId}", productId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(product)))
            .andReturn();

        // Manually check the status
        assertEquals(500, result.getResponse().getStatus());
    }
    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    void testUpdateProduct() throws Exception {
        String productId = "1";
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Updated Product");

        when(productService.findById(productId)).thenReturn(CompletableFuture.completedFuture(product));
        when(productService.update(any(Product.class))).thenReturn(product);

        mockMvc.perform(put("/product/admin/updateProduct/{productId}", productId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(product)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.productName").value(product.getProductName()));

        verify(productService, times(1)).update(any(Product.class));
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteProduct() {
        String productId = "123";
        Product product = new Product();
        product.setProductId(productId);

        CompletableFuture<Product> futureProduct = CompletableFuture.completedFuture(product);
        when(productServices.findById(productId)).thenReturn(futureProduct);
        when(productServices.delete(productId)).thenReturn(CompletableFuture.completedFuture(null));

        ResponseEntity<?> responseEntity = productController.deleteProduct(productId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(product, responseEntity.getBody()); 

        verify(productServices, times(1)).findById(productId);
        verify(productServices, times(1)).delete(productId);
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    void testDeleteProduct_NotFound() {
        String productId = "123";

        CompletableFuture<Product> futureProduct = CompletableFuture.completedFuture(null);
        when(productServices.findById(productId)).thenReturn(futureProduct);

        ResponseEntity<?> responseEntity = productController.deleteProduct(productId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        verify(productServices, times(1)).findById(productId); // findById should be called
        verify(productServices, never()).delete(productId); // delete should not be called
    }


    @Test
    @WithMockUser(username="user", roles={"USER"})
    void testGetAllProducts_SortByName() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        CompletableFuture<List<Product>> futureProducts = CompletableFuture.completedFuture(products);

        when(productService.findAll(any(SortByName.class))).thenReturn(futureProducts);

        mockMvc.perform(get("/product/public/getAllProducts").param("sort", "SortByName"))
                .andExpect(status().isOk());

        verify(productService, times(1)).findAll(any(SortByName.class));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    void testGetAllProducts_SortByDate() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        CompletableFuture<List<Product>> futureProducts = CompletableFuture.completedFuture(products);

        when(productService.findAll(any(SortByDate.class))).thenReturn(futureProducts);

        mockMvc.perform(get("/product/public/getAllProducts").param("sort", "SortByDate"))
                .andExpect(status().isOk());

        verify(productService, times(1)).findAll(any(SortByDate.class));
    }


    @Test
    @WithMockUser(username="user", roles={"USER"})
    void testGetAllProducts_SortByPrice() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        CompletableFuture<List<Product>> futureProducts = CompletableFuture.completedFuture(products);

        when(productService.findAll(any(SortByPrice.class))).thenReturn(futureProducts);

        mockMvc.perform(get("/product/public/getAllProducts").param("sort", "SortByPrice"))
                .andExpect(status().isOk());

        verify(productService, times(1)).findAll(any(SortByPrice.class));
    }



    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    void testUpdateProduct_NotFound() throws Exception {
        String productId = "nonExistentId";
        Product product = new Product();
        product.setProductId(productId);

        // Return null wrapped in a CompletableFuture to simulate "not found"
        when(productService.findById(productId)).thenReturn(CompletableFuture.completedFuture(null));

        mockMvc.perform(put("/product/admin/updateProduct/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).findById(productId);
        verify(productService, never()).update(any(Product.class));
    }
    @Test
    @WithMockUser(username="user", roles={"USER"})
    void testGetProductById_NotFound() {
        String productId = "nonExistentId";

        // Return null wrapped in a CompletableFuture to simulate "not found"
        when(productServices.findById(productId)).thenReturn(CompletableFuture.completedFuture(null));

        ResponseEntity<?> responseEntity = productController.getProductById(productId).join();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(productServices, times(1)).findById(productId);
    }
    @Test
    @WithMockUser(username="user", roles={"USER"})
    void testGetAllProducts_NullSortExplicit() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        CompletableFuture<List<Product>> futureProducts = CompletableFuture.completedFuture(products);

        when(productService.findAll(null)).thenReturn(futureProducts);

        mockMvc.perform(get("/product/public/getAllProducts").param("sort", (String) null))
                .andExpect(status().isOk());

        verify(productService, times(1)).findAll(null); // Verify that null was explicitly handled
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    void testGetAllProducts_NoSortParam() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        CompletableFuture<List<Product>> futureProducts = CompletableFuture.completedFuture(products);

        when(productService.findAll(null)).thenReturn(futureProducts);

        mockMvc.perform(get("/product/public/getAllProducts"))
                .andExpect(status().isOk());

        verify(productService, times(1)).findAll(null); // Verify that no sort parameter results in no sorting strategy
    }


    @Test
    @WithMockUser(username="user", roles={"USER"})
    void testGetAllProducts_UndefinedSort() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        CompletableFuture<List<Product>> futureProducts = CompletableFuture.completedFuture(products);

        when(productService.findAll(any())).thenReturn(futureProducts);

        mockMvc.perform(get("/product/public/getAllProducts").param("sort", "undefined sort"))
                .andExpect(status().isOk());

        verify(productService, times(1)).findAll(any()); // Verify that some sorting strategy is still used, but properly expect a CompletableFuture
    }
}
