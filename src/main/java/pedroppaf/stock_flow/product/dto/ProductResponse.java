package pedroppaf.stock_flow.product.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ProductResponse (
        Long id,
        String sku,
        String name,
        String barcode,
        BigDecimal costPrice,
        BigDecimal salePrice,
        Integer stockMin,
        String unit,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
