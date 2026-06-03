package pedroppaf.stock_flow.product.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pedroppaf.stock_flow.product.dto.ProductRequest;
import pedroppaf.stock_flow.product.dto.ProductResponse;
import pedroppaf.stock_flow.product.mapper.ProductMapper;
import pedroppaf.stock_flow.product.model.Product;
import pedroppaf.stock_flow.product.service.ProductService;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request, UriComponentsBuilder uriBuilder) {
        Product saved = productService.create(request);
        URI uri = uriBuilder.path("/api/products/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).body(ProductMapper.toResponse(saved));
    }

    @GetMapping
    public List<ProductResponse> listProducts() {
        return productService.findAll().stream().map(ProductMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        Product p = productService.findById(id);
        return ResponseEntity.ok(ProductMapper.toResponse(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        Product updated = productService.update(id, request);
        return ResponseEntity.ok(ProductMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.delete(id);
    }
}
