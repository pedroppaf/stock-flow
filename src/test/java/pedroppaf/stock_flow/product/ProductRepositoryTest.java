package pedroppaf.stock_flow.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void saveAndFindBySku(){
        Product p = new Product();
        p.setSku("SKU-001");
        p.setName("Produto Teste");
        p.setSalePrice(new BigDecimal("9.90"));
        p.setCostPrice(new BigDecimal("5.00"));

        Product saved = productRepository.save(p);

        assertThat(saved.getId()).isNotNull();
        assertThat(productRepository.findBySku("SKU-001")).isPresent();
    }
}
