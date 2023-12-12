package pt.ulisboa.tecnico.rnl.dei.deixmas.main.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.dto.RaffleDto;

@Data
@Entity
@Table(name = "raffles")
public class Raffle {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "theme", nullable = false)
	private String theme;

	@Column(name = "date", nullable = false, unique = true)
	private String date;

	@Column(name = "normalOption", nullable = false)
	private Long normalOption;

	@Column(name = "vegetarianOption", nullable = false)
	private Long vegetarianOption;

    @ManyToMany(mappedBy = "participating")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Participant> participants;

	//@Column(name = "image", nullable = false)
	//private String image;

	protected Raffle() {
	}

	public Raffle(String theme, String date,
                Long normalOption, Long vegetarianOption) {
		this.theme = theme;
		this.date= date;
		this.normalOption= normalOption;
		this.vegetarianOption= vegetarianOption;
	}

	public Raffle(RaffleDto raffleDto) {
		this(raffleDto.theme(), raffleDto.date(),
                raffleDto.normalOption(), raffleDto.vegetarianOption());
	}
}

