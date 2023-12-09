package pt.ulisboa.tecnico.rnl.dei.deixmas.main.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.dto.ParticipantDto;

@Data
@Entity
@Table(name = "participants")
public class Participant {

	public enum ParticipantType {
		TEACHER, GRANTEE
	}

	public enum ParticipantDiet{
		REGULAR, VEGETARIAN
	}

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "ist_id", nullable = false, unique = true)
	private String istId;

	@Column(name = "type", nullable = false)
	private ParticipantType type;

	@Column(name = "diet", nullable = false)
	private ParticipantDiet diet;

	protected Participant() {
	}

	public Participant(String name, String istId, ParticipantType type) {
		this.name = name;
		this.istId = istId;
		this.type = type;
	}

	public Participant(ParticipantDto participantDto) {
		this(participantDto.name(), participantDto.istId(),
				ParticipantType.valueOf(participantDto.type().toUpperCase()));
	}
}
