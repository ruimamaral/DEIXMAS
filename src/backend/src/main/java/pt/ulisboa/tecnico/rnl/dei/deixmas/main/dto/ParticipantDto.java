package pt.ulisboa.tecnico.rnl.dei.deixmas.main.dto;

import pt.ulisboa.tecnico.rnl.dei.deixmas.main.domain.Participant;

// Data Transfer Object, to communicate with frontend
public record ParticipantDto(long id, String name, String istId, String type) {
	public ParticipantDto(Participant participant) {
		this(participant.getId(), participant.getName(), participant.getIstId(),
				participant.getType().toString());
	}
}
