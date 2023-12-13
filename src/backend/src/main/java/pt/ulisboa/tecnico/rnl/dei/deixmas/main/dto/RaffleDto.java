package pt.ulisboa.tecnico.rnl.dei.deixmas.main.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import pt.ulisboa.tecnico.rnl.dei.deixmas.main.domain.Raffle;

// Data Transfer Object, to communicate with frontend
public record RaffleDto(
		@NotNull @PositiveOrZero(message = "Invalid Id") long id,
		@NotBlank(message = "Raffle must have a theme") String theme,
		@NotBlank @Pattern(
            regexp = "^([0-9]{2})?[0-9]{2}-(1[0-2]|0?[1-9])-(3[01]|[12][0-9]|0?[1-9])$",
			message = "Invalid date format") String date,
		@NotNull @PositiveOrZero(message = "Invalid regular option") long normalOption,
		@NotNull @PositiveOrZero(message = "Invalid vegetarian option") long vegetarianOption,
		@NotNull boolean orderPlaced
	) {

	public RaffleDto(Raffle raffle) {
		this(raffle.getId(), raffle.getTheme(), raffle.getDate(),
                raffle.getNormalOption(), raffle.getVegetarianOption(), raffle.getOrderPlaced());
	}
}
