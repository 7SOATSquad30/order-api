package br.com.fiap.grupo30.fastfood.order_api.domain.usecases.product;

import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductUseCase {

    @Value("${external.services.products-api.endpoint}")
    private String productsApiEndpoint;

    public Product findProductById(Long id) {
        String uri = this.productsApiEndpoint + "/products/" + id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Product> response = restTemplate.getForEntity(uri, Product.class);
        return response.getBody();
    }
}
