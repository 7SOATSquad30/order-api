package br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto;

import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities.CategoryEntity;
import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities.ProductEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDTO {

    private Long productId;

    @NotBlank(message = "Campo requirido")
    private String name;

    @NotBlank(message = "Campo requirido")
    private String description;

    @Positive(message = "Preço deve ser um valor positivo") private Double price;

    @NotBlank(message = "Campo requirido")
    private String imgUrl;

    private String category;

    private CategoryEntity categoryEntity;

    public ProductEntity toPersistence() {
        return new ProductEntity(
                productId, name, description, price, imgUrl, categoryEntity.toPersistence());
    }

    public ProductDTO(
            Long productId,
            String name,
            String description,
            Double price,
            String imgUrl,
            String category) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.category = category;
    }
}
