package pedroppaf.stock_flow.product.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pedroppaf.stock_flow.product.dto.ProductRequest;
import pedroppaf.stock_flow.product.exception.ProductAlreadyExistsException;
import pedroppaf.stock_flow.product.model.Product;
import pedroppaf.stock_flow.product.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repo;

    @InjectMocks
    private ProductService service;

    @Test
    void create_shouldSave_whenNoConflicts() {
        var req = new ProductRequest("SKU-XX", "Produto X", null, new BigDecimal("1.0"), new BigDecimal("2.0"), 0, "UN");
        when(repo.findBySku("SKU-XX")).thenReturn(Optional.empty());
        when(repo.save(any())).thenReturn(new Product());

        service.create(req);

        verify(repo).save(any(Product.class));
    }

    @Test
    void create_shouldThrow_whenSkuExists() {
        var req = new ProductRequest("SKU-EX", "Produto", null, BigDecimal.ZERO, BigDecimal.ONE, 0,"UN");
        when(repo.findBySku("SKU-EX")).thenReturn(Optional.of(new Product()));

        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ProductAlreadyExistsException.class);
        verify(repo, never()).save(any());
    }
}
