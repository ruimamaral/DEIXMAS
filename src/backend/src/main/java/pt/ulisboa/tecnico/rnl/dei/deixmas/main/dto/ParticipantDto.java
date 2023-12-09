package pt.ulisboa.tecnico.rnl.dei.deixmas.main.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import pt.ulisboa.tecnico.rnl.dei.deixmas.main.domain.Participant;

// Data Transfer Object, to communicate with frontend
public record ParticipantDto(
		@Positive(message = "Invalid Id") long id,
		@NotBlank(message = "Participant must have a name") String name,
		@NotNull @Pattern(
			regexp = "^ist[0-9]+$",
			message = "istID should abide the appropriate format") String istId,
		@NotBlank @Pattern(
			regexp = "^(GRANTEE|TEACHER)$",
			message = "Invalid participant type"
		) String type,
		@NotBlank @Pattern(
			regexp = "^(REGULAR|VEGETARIAN)$",
			message = "Invalid participant diet"
		) String diet
	) {

	public ParticipantDto(Participant participant) {
		this(participant.getId(), participant.getName(), participant.getIstId(),
				participant.getType().toString(), participant.getDiet().toString());
	}
}
