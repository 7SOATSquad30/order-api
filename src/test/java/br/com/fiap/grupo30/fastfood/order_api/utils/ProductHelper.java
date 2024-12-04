package br.com.fiap.grupo30.fastfood.order_api.utils;

import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Category;
import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Product;

public class ProductHelper {
    private static final String DEFAULT_NAME = "Burger";
    private static final String DEFAULT_DESCRIPTION = "Delicious burger";
    private static final Double DEFAULT_PRICE = 12.99;
    private static final String DEFAULT_IMAGE_URL = "http://example.com/burger.png";
    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final String DEFAULT_CATEGORY_NAME = "Snacks";

    public static Product valid() {
        Long productId = (long) (Math.random() * 1000000000);
        return new Product(
                productId,
                "Acme Box",
                "Box",
                10.50,
                "https://examples.com/products/acme-box",
                validCategory("Boxes"));
    }

    public static Product validById(Long productId) {
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

    public static Product createDefaultProduct() {
        return createProduct(
                null,
                DEFAULT_NAME,
                DEFAULT_DESCRIPTION,
                DEFAULT_PRICE,
                DEFAULT_IMAGE_URL,
                createDefaultCategory());
    }

    public static Product createProduct(
            Long id,
            String name,
            String description,
            double price,
            String imgUrl,
            Category category) {
        Product product = Product.create(name, description, price, imgUrl, category);
        product.setId(id);
        return product;
    }

    public static Product createDefaultProductWithId(Long id) {
        return createProduct(
                id,
                DEFAULT_NAME,
                DEFAULT_DESCRIPTION,
                DEFAULT_PRICE,
                DEFAULT_IMAGE_URL,
                createDefaultCategory());
    }

    public static Category createDefaultCategory() {
        return new Category(DEFAULT_CATEGORY_ID, DEFAULT_CATEGORY_NAME);
    }

    public static Category createCategory(Long id, String name) {
        return new Category(id, name);
    }
}
