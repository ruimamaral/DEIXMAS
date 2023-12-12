package pt.ulisboa.tecnico.rnl.dei.deixmas.main.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.dto.ParticipantDto;

@Data
@Entity
@Table(name = "participants")
public class Participant {

	public enum ParticipantType {
		TEACHER, GRANTEE
	}

	public enum ParticipantDiet {
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

	@ManyToMany
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JoinTable(
  		name = "participate", 
  		joinColumns = @JoinColumn(name = "participant_id"), 
  		inverseJoinColumns = @JoinColumn(name = "raffle_id"))
	private Set<Raffle> participating;

	protected Participant() {
	}

	public Participant(String name, String istId,
				ParticipantType type, ParticipantDiet diet) {
		this.name = name;
		this.istId = istId;
		this.type = type;
		this.diet = diet;
	}

	public Participant(ParticipantDto participantDto) {
		this(participantDto.name(), participantDto.istId(),
				ParticipantType.valueOf(participantDto.type().toUpperCase()),
				ParticipantDiet.valueOf(participantDto.diet().toUpperCase()));
	}
}
