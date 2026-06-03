package pedroppaf.stock_flow.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pedroppaf.stock_flow.product.dto.ProductRequest;
import pedroppaf.stock_flow.product.model.Product;
import pedroppaf.stock_flow.product.service.ProductService;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService service;

    @Test
    void create_shouldReturnCreated() throws Exception {
        var req = new ProductRequest("SKU-1", "Prod 1", null, new BigDecimal("1.0"), new BigDecimal("2.0"), 0, "UN");
        Product saved = new Product();
        saved.setId(10L);
        saved.setSku("SKU-1");
        saved.setName("Prod 1");
        when(service.create(any())).thenReturn(saved);

        mvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))
                        .with(user("user"))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.containsString("/api/products/10")))
                .andExpect(jsonPath("$.id").value(10));
    }
}
