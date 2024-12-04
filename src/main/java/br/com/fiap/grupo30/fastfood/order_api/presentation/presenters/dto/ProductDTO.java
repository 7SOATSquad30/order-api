package br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto;

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
}
