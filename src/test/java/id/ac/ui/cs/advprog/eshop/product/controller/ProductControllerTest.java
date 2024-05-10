package id.ac.ui.cs.advprog.eshop.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.eshop.product.model.Product;
import id.ac.ui.cs.advprog.eshop.product.service.ProductService;
import id.ac.ui.cs.advprog.eshop.product.service.SortByDate;
import id.ac.ui.cs.advprog.eshop.product.service.SortByName;
import id.ac.ui.cs.advprog.eshop.product.service.SortByPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())  // Add this to apply Spring Security configuration
                .build();
    }

    @Test
    @WithMockUser(username="user", roles={"USER"}) 
    public void testGetAllProducts() throws Exception {
        // Mocking the service response
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productService.findAll(null)).thenReturn(products);

        mockMvc.perform(get("/product/getAllProducts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(products.size()))
                .andReturn().getResponse().getContentAsString();  // Captures the response content

        verify(productService, times(1)).findAll(null);
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    public void testGetProductById() throws Exception {
        String productId = "1";
        Product product = new Product();
        product.setProductId(productId);

        when(productService.findById(productId)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/product/getProductById/{productId}", productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(productId));

        verify(productService, times(1)).findById(productId);
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void testCreateProduct() throws Exception {
        Product product = new Product();
        product.setProductName("Test Product");

        when(productService.create(any(Product.class))).thenReturn(product);
        mockMvc.perform(post("/product/admin/createProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productName").value(product.getProductName()));

        verify(productService, times(1)).create(any(Product.class));
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void testUpdateProduct() throws Exception {
        String productId = "1";
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Updated Product");

        when(productService.findById(productId)).thenReturn(Optional.of(product));
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
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void testDeleteProduct() throws Exception {
        String productId = "1";

        when(productService.delete(productId)).thenReturn(true);

        mockMvc.perform(delete("/product/admin/deleteProduct/{productId}", productId))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).delete(productId);
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    public void testGetAllProducts_SortByName() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productService.findAll(any(SortByName.class))).thenReturn(products);

        mockMvc.perform(get("/product/getAllProducts").param("sort", "SortByName"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(products.size()));

        verify(productService, times(1)).findAll(any(SortByName.class));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    public void testGetAllProducts_SortByDate() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productService.findAll(any(SortByDate.class))).thenReturn(products);

        mockMvc.perform(get("/product/getAllProducts").param("sort", "SortByDate"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(products.size()));

        verify(productService, times(1)).findAll(any(SortByDate.class));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    public void testGetAllProducts_SortByPrice() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productService.findAll(any(SortByPrice.class))).thenReturn(products);

        mockMvc.perform(get("/product/getAllProducts").param("sort", "SortByPrice"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(products.size()));

        verify(productService, times(1)).findAll(any(SortByPrice.class));
    }
    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void testDeleteProduct_NotFound() throws Exception {
        String productId = "nonExistentId";

        when(productService.delete(productId)).thenReturn(false);

        mockMvc.perform(delete("/product/admin/deleteProduct/{productId}", productId))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).delete(productId);
    }

    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    public void testUpdateProduct_NotFound() throws Exception {
        String productId = "nonExistentId";
        Product product = new Product();
        product.setProductId(productId);

        when(productService.findById(productId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/product/admin/updateProduct/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).findById(productId);
        verify(productService, never()).update(any(Product.class));
    }
    @Test
    @WithMockUser(username="user", roles={"USER"})
    public void testGetProductById_NotFound() throws Exception {
        String productId = "nonExistentId";

        when(productService.findById(productId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/product/getProductById/{productId}", productId))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).findById(productId);
    }
    @Test
    @WithMockUser(username="user", roles={"USER"})
    public void testGetAllProducts_UndefinedSort() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productService.findAll(any())).thenReturn(products); // expect any strategy

        mockMvc.perform(get("/product/getAllProducts").param("sort", "undefinedSort"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(products.size()));

        verify(productService, times(1)).findAll(any()); // verify that some sorting strategy is still used
    }

}
