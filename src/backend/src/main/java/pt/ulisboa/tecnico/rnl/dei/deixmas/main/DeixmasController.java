package pt.ulisboa.tecnico.rnl.dei.deixmas.main;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pt.ulisboa.tecnico.rnl.dei.deixmas.main.dto.BasketDto;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.dto.OrderDto;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.dto.ParticipantDto;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.dto.RaffleDto;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.service.BasketService;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.service.OrderService;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.service.ParticipantService;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.service.RaffleService;

@Validated
@RestController
public class DeixmasController {
	@Autowired
	private ParticipantService participantService;
	@Autowired
	private BasketService basketService;
	@Autowired
	private RaffleService raffleService;
	@Autowired
	private OrderService orderService;

	@GetMapping("/participants")
	public List<ParticipantDto> getParticipants() {
		return participantService.getAllParticipants();
	}

	@PostMapping("/participants")
	public ParticipantDto createParticipant(@Valid @RequestBody ParticipantDto participantDto) {
		return participantService.createParticipant(participantDto);
	}

	@GetMapping("/participants/{id}")
	public ParticipantDto getParticipant(@NotNull @PositiveOrZero @PathVariable long id) {
		return participantService.getParticipant(id);
	}

	@PutMapping("/participants/{id}")
	public ParticipantDto updateParticipant(@NotNull @PositiveOrZero @PathVariable long id,
				@Valid @RequestBody ParticipantDto participantDto) {
		return participantService.updateParticipant(id, participantDto);
	}

	@DeleteMapping("/participants/{id}")
	public void deleteParticipant(@NotNull @PositiveOrZero @PathVariable long id) {
		participantService.deleteParticipant(id);
	}

	@GetMapping("/participants/{id}/raffles")
	public List<RaffleDto> getParticipating(@NotNull @PositiveOrZero @PathVariable long id) {
		return participantService.getAllParticipating(id);
	}

	@PostMapping("/participants/{id}/raffles/{raffle}")
	public void participate(@NotNull @PositiveOrZero @PathVariable long id,
				@NotNull @Positive @PathVariable long raffle) {
		participantService.participate(id, raffle);
	}

	@DeleteMapping("/participants/{id}/raffles/{raffle}")
	public void leaveRaffle(@NotNull @PositiveOrZero @PathVariable long id,
				@NotNull @PositiveOrZero @PathVariable long raffle) {
		participantService.leaveRaffle(id, raffle);
	}

	@GetMapping("/baskets")
	public List<BasketDto> getBaskets() {
		return basketService.getAllBaskets();
	}

	@GetMapping("/raffles")
	public List<RaffleDto> getRaffles() {
		return raffleService.getAllRaffles();
	}

	@PostMapping("/raffles")
	public RaffleDto createRaffle(@Valid @RequestBody RaffleDto raffleDto) {
		return raffleService.createRaffle(raffleDto);
	}

	@GetMapping("/raffles/{id}")
	public RaffleDto getRaffle(@NotNull @PositiveOrZero @PathVariable long id) {
		return raffleService.getRaffle(id);
	}

	@PutMapping("/raffles/{id}")
	public RaffleDto updateRaffle(@NotNull @PositiveOrZero @PathVariable long id,
				@Valid @RequestBody RaffleDto raffleDto) {
		return raffleService.updateRaffle(id, raffleDto);
	}

	@DeleteMapping("/raffles/{id}")
	public void deleteRaffle(@NotNull @PositiveOrZero @PathVariable long id) {
		raffleService.deleteRaffle(id);
	}

	@GetMapping("/raffles/{id}/participants")
	public List<ParticipantDto> getParticipants(@NotNull @PositiveOrZero @PathVariable long id) {
		return raffleService.getAllParticipants(id);
	}

	@PostMapping("/raffles/{id}/order")
	public OrderDto placeOrder(@NotNull @PositiveOrZero @PathVariable long id) {
		return orderService.placeOrder(id);
	}
}
