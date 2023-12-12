package pt.ulisboa.tecnico.rnl.dei.deixmas.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.deixmas.exceptions.DeixmasException;
import pt.ulisboa.tecnico.rnl.dei.deixmas.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.domain.Raffle;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.dto.ParticipantDto;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.dto.RaffleDto;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.repository.RaffleRepository;

@Service
@Transactional
public class RaffleService {

	@Autowired
	private RaffleRepository raffleRepository;

	Raffle fetchRaffleOrThrow(long id) {
		return raffleRepository.findById(id)
				.orElseThrow(() -> new DeixmasException(ErrorMessage.NO_SUCH_RAFFLE, Long.toString(id)));
	}

	private RaffleDto insertRaffle(Long id, RaffleDto raffleDto) {
		Raffle raffle = new Raffle(raffleDto);
		raffle.setId(id);
		return new RaffleDto(raffleRepository.save(raffle));
	}

	@Transactional
	public List<RaffleDto> getAllRaffles() {
		return raffleRepository.findAll().stream()
				.map(RaffleDto::new)
				.toList();
	}

	@Transactional
	public RaffleDto createRaffle(RaffleDto raffleDto) {
		return insertRaffle(null, raffleDto);
	}

	@Transactional
	public RaffleDto getRaffle(long id) {
		return new RaffleDto(fetchRaffleOrThrow(id));
	}

	@Transactional
	public RaffleDto updateRaffle(long id, RaffleDto raffleDto) {
		fetchRaffleOrThrow(id); // ensure exists

		return insertRaffle(id, raffleDto);
	}

	@Transactional
	public void deleteRaffle(long id) {
		fetchRaffleOrThrow(id); // ensure exists

		raffleRepository.deleteById(id);
	}

	@Transactional
	public List<ParticipantDto> getAllParticipants(long id) {
		return fetchRaffleOrThrow(id).getParticipants().stream()
				.map(ParticipantDto::new)
				.toList();
	}
}
