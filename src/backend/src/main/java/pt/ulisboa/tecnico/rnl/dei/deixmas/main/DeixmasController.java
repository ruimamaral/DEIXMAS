package pt.ulisboa.tecnico.rnl.dei.deixmas.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pt.ulisboa.tecnico.rnl.dei.deixmas.main.dto.ParticipantDto;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.service.ParticipantService;


@RestController
public class DeixmasController {
	@Autowired
	private ParticipantService participantService;

	@GetMapping("/participants")
	public List<ParticipantDto> getParticipants() {
		//return participantService.getAllParticipants();
		return List.of(new ParticipantDto(1, "John Doe", "ist12345", "STUDENT"));
	}

	@PostMapping("/participants")
	public ParticipantDto createParticipant(@RequestBody ParticipantDto participantDto) {
		return participantService.createParticipant(participantDto);
	}

	@GetMapping("/participants/{id}")
	public ParticipantDto getParticipant(@PathVariable long id) {
		return participantService.getParticipant(id);
	}

	@PutMapping("/participants/{id}")
	public ParticipantDto updateParticipant(@PathVariable long id, @RequestBody ParticipantDto participantDto) {
		return participantService.updateParticipant(id, participantDto);
	}

	@DeleteMapping("/participants/{id}")
	public void deleteParticipant(@PathVariable long id) {
		participantService.deleteParticipant(id);
	}
}
