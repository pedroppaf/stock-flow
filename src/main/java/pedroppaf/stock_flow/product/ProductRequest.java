package pedroppaf.stock_flow.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductRequest(
        @Size(max = 64)
        String sku,

        @NotBlank
        @Size(max = 200)
        String name,

        @Size
        String barcode,

        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal costPrice,

        @DecimalMin(value = "0.0", inclusive = true)
        BigDecimal salePrice,

        @Size(max = 20)
        String unit,

        @Min(0)
        Integer stockMin
        ) {

        public Product toEntity() {
                Product product = new Product();
                product.setSku(sku());
                product.setName(name());
                product.setBarcode(barcode());
                product.setCostPrice(costPrice() != null ? costPrice() : BigDecimal.ZERO);
                product.setSalePrice(salePrice() != null ? salePrice() : BigDecimal.ZERO);
                product.setUnit(unit());
                product.setStockMin(stockMin() != null ? stockMin() : 0);
                return product;
        }
}
