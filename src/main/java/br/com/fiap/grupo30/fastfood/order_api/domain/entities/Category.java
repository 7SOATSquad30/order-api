package br.com.fiap.grupo30.fastfood.order_api.domain.entities;

import br.com.fiap.grupo30.fastfood.order_api.infrastructure.persistence.entities.CategoryEntity;
import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.CategoryDTO;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Category {

    private Long id;
    private String name;

    public static Category create(String name) {
        return new Category(null, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public CategoryDTO toDTO() {
        return new CategoryDTO(id, name);
    }

    public CategoryEntity toPersistence() {
        return new CategoryEntity(id, name);
    }
}
