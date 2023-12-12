package pt.ulisboa.tecnico.rnl.dei.deixmas.main.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


// Data Transfer Object, to communicate with frontend
public record OrderDto(
		@NotBlank @Pattern(
			regexp = "^([0-9]{2})?[0-9]{2}-(1[0-2]|0?[1-9])-(3[01]|[12][0-9]|0?[1-9])$",
			message = "Invalid date format") String date,
		@NotNull BasketDto normalOption,
		@NotNull BasketDto vegetarianOption
	) {
}
