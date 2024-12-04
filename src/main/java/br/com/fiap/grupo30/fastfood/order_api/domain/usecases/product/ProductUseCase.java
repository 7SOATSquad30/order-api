package br.com.fiap.grupo30.fastfood.order_api.domain.usecases.product;

import br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductUseCase {

    @Value("${external.services.products-api.endpoint}")
    private String productsApiEndpoint;

    public ProductDTO findProductById(Long id) {
        String uri = this.productsApiEndpoint + "/products/" + id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ProductDTO> response = restTemplate.getForEntity(uri, ProductDTO.class);
        return response.getBody();
    }
}
