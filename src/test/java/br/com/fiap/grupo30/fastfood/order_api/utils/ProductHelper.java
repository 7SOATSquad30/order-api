package br.com.fiap.grupo30.fastfood.order_api.utils;

import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Category;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Product;

public class ProductHelper {
    public static Product validProduct() {
        Long productId = (long) (Math.random() * 1000000000);
        return new Product(
                productId,
                "Acme Box",
                "Box",
                10.50,
                "https://examples.com/products/acme-box",
                validCategory("Boxes"));
    }

    public static Category validCategory(String categoryName) {
        Long categoryId = (long) (Math.random() * 1000000000);
        return new Category(categoryId, categoryName);
    }
}
