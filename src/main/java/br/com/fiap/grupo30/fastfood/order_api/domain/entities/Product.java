package br.com.fiap.grupo30.fastfood.order_api.domain.entities;

import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities.ProductEntity;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.ProductDTO;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Product {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imgUrl;
    private Category category;

    public static Product create(
            String name, String description, Double price, String imgUrl, Category category) {
        return new Product(null, name, description, price, imgUrl, category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public ProductDTO toDTO() {
        return new ProductDTO(id, name, description, price, imgUrl, category.getName());
    }

    public ProductEntity toPersistence() {
        return new ProductEntity(id, name, description, price, imgUrl, category.toPersistence());
    }
}
