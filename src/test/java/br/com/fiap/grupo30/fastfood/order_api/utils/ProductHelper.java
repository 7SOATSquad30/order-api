package br.com.fiap.grupo30.fastfood.order_api.utils;

import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.ProductDTO;

public class ProductHelper {
    private static final Long DEFAULT_PRODUCTID = 1L;

    public static Long valid() {
        return DEFAULT_PRODUCTID;
    }

    public static ProductDTO validProduct(Long productId) {
        return new ProductDTO(
                productId, "Acme Box", "Awesome Box", 10.50, "https://test.com", "boxes");
    }
}
