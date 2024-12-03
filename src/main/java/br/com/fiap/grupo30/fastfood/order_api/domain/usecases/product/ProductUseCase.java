package br.com.fiap.grupo30.fastfood.order_api.domain.usecases.product;

import br.com.fiap.grupo30.fastfood.order_api.domain.entities.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductUseCase {

    public Product findProductById(Long id) {
        String uri = "http://localhost:8080/products_api/" + id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Product> response = restTemplate.getForEntity(uri, Product.class);
        return response.getBody();
    }
}
