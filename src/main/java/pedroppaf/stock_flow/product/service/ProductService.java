package pedroppaf.stock_flow.product.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pedroppaf.stock_flow.product.dto.ProductRequest;
import pedroppaf.stock_flow.product.exception.ProductAlreadyExistsException;
import pedroppaf.stock_flow.product.exception.ProductNotFoundException;
import pedroppaf.stock_flow.product.mapper.ProductMapper;
import pedroppaf.stock_flow.product.model.Product;
import pedroppaf.stock_flow.product.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create (ProductRequest request) {
        if(request.sku() != null && productRepository.findBySku(request.sku()).isPresent()){
            throw new ProductAlreadyExistsException("SKU already exists: " + request.sku());
        }
        if (request.barcode() != null && productRepository.findByBarcode(request.barcode()).isPresent()) {
            throw new ProductAlreadyExistsException("Barcode already exists: " + request.barcode());
        }
        Product product = ProductMapper.toEntity(request);
        return productRepository.save(product);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product update(Long id, ProductRequest request){
        Product existing = findById(id);

        if(request.sku() != null){
            Optional<Product> bySku = productRepository.findBySku(request.sku());
            if(bySku.isPresent() && !bySku.get().getId().equals(id)) {
                throw new ProductAlreadyExistsException("SKU already exists: " + request.sku());
            }
            existing.setSku(request.sku());
        }else {
            existing.setSku(null);
        }

        existing.setName(request.name());
        existing.setCostPrice(request.costPrice() != null ? request.costPrice() : existing.getCostPrice());
        existing.setSalePrice(request.salePrice() != null ? request.salePrice() : existing.getSalePrice());
        existing.setStockMin(request.stockMin() != null ? request.stockMin() : existing.getStockMin());
        existing.setUnit(request.unit());

        return productRepository.save(existing);
    }

    public void delete(Long id){
        Product product = findById(id);
        productRepository.delete(product);
    }
}
