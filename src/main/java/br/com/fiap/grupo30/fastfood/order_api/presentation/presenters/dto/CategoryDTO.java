package br.com.fiap.grupo30.fastfood.order_api.presentation.presenters.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryDTO {

    private Long categoryId;

    @NotBlank(message = "Campo requirido")
    private String name;
}
