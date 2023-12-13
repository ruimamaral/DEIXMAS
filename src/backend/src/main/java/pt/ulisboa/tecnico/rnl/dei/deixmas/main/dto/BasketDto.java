package pt.ulisboa.tecnico.rnl.dei.deixmas.main.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;


// Data Transfer Object, to communicate with frontend
public record BasketDto(
		@NotNull @PositiveOrZero(message = "Invalid Id") long id,
		@NotBlank(message = "Basket name missing") String name,
		@NotBlank(message = "Basket description missing") String description,
		@NotNull @PositiveOrZero(message = "Invalid unitPrice") long unitPrice,
		@NotNull @PositiveOrZero(message = "Invalid unitWeight") long unitWeight,
		@NotBlank @Pattern(
			regexp = "^(true|false)$",
			message = "Invalid vegetarian field"
		) boolean vegetarian
	) {
}
