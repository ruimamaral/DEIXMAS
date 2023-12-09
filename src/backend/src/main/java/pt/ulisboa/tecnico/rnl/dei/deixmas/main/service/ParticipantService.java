package pt.ulisboa.tecnico.rnl.dei.deixmas.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pt.ulisboa.tecnico.rnl.dei.deixmas.exceptions.DeixmasException;
import pt.ulisboa.tecnico.rnl.dei.deixmas.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.domain.Participant;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.dto.ParticipantDto;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.repository.ParticipantRepository;

@Service
@Transactional
public class ParticipantService {

	@Autowired
	private ParticipantRepository participantRepository;

	private Participant fetchParticipantOrThrow(long id) {
		return participantRepository.findById(id)
				.orElseThrow(() -> new DeixmasException(ErrorMessage.NO_SUCH_PARTICIPANT, Long.toString(id)));
	}

	private ParticipantDto insertParticipant(Long id, ParticipantDto participantDto) {
		Participant participant = new Participant(participantDto);
		participant.setId(id);
		return new ParticipantDto(participantRepository.save(participant));
	}

	@Transactional
	public List<ParticipantDto> getAllParticipants() {
		return participantRepository.findAll().stream()
				.map(ParticipantDto::new)
				.toList();
	}

	@Transactional
	public ParticipantDto createParticipant(ParticipantDto participantDto) {
		return insertParticipant(null, participantDto);
	}

	@Transactional
	public ParticipantDto getParticipant(long id) {
		return new ParticipantDto(fetchParticipantOrThrow(id));
	}

	@Transactional
	public ParticipantDto updateParticipant(long id, ParticipantDto participantDto) {
		fetchParticipantOrThrow(id); // ensure exists

		return insertParticipant(id, participantDto);
	}

	@Transactional
	public void deleteParticipant(long id) {
		fetchParticipantOrThrow(id); // ensure exists

		participantRepository.deleteById(id);
	}
}
