package pedroppaf.stock_flow.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pedroppaf.stock_flow.product.model.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySku(String sku);
    Optional<Product> findByBarcode(String name);
}
