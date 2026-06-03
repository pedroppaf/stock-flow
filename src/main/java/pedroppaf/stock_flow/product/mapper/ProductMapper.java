package pedroppaf.stock_flow.product.mapper;

import pedroppaf.stock_flow.product.dto.ProductRequest;
import pedroppaf.stock_flow.product.dto.ProductResponse;
import pedroppaf.stock_flow.product.model.Product;

import java.math.BigDecimal;

public final class ProductMapper {

    private ProductMapper() {}

    public static Product toEntity(ProductRequest request) {
        Product p = new Product();
        p.setSku(request.sku());
        p.setName(request.name());
        p.setBarcode(request.barcode());
        p.setCostPrice(request.costPrice() != null ? request.costPrice() : BigDecimal.ZERO);
        p.setSalePrice(request.salePrice() != null ? request.salePrice() : BigDecimal.ZERO);
        p.setStockMin(request.stockMin() != null ? request.stockMin() : 0);
        p.setUnit(request.unit());
        return p;
    }

    public static ProductResponse toResponse(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getSku(),
                p.getName(),
                p.getBarcode(),
                p.getCostPrice(),
                p.getSalePrice(),
                p.getStockMin(),
                p.getUnit(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}
