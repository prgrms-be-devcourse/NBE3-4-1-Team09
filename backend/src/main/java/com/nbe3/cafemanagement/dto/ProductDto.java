package com.nbe3.cafemanagement.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50, message = "Name must be less than 50 characters")
    private String name;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive value")
    private Integer price;

    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @Size(max = 1024, message = "Description must be less than 1024 characters")
    private String imageUrl;
}
