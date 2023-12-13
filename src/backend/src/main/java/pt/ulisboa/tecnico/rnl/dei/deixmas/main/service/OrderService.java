package pt.ulisboa.tecnico.rnl.dei.deixmas.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pt.ulisboa.tecnico.rnl.dei.deixmas.config.BasketStoreConfiguration;
import pt.ulisboa.tecnico.rnl.dei.deixmas.exceptions.DeixmasException;
import pt.ulisboa.tecnico.rnl.dei.deixmas.exceptions.ErrorMessage;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.domain.Raffle;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.dto.OrderDto;

@Service
public class OrderService {

	@Autowired
	private BasketStoreConfiguration apiConfig;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RaffleService raffleService;

	public OrderDto placeOrder(long id) {
		Raffle raffle = raffleService.fetchRaffleOrThrow(id);

		if (raffle.getOrderPlaced()) {
			throw new DeixmasException(ErrorMessage.ALREADY_ORDERED, Long.toString(id));
		}

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + apiConfig.getKey());
		headers.setContentType(MediaType.APPLICATION_JSON);

		String requestBody = String.format("{ \"normalOptionBasketId\": %d, \"vegetarianOptionBasketId\": %d }",
				raffle.getNormalOption(), raffle.getVegetarianOption());

		HttpEntity<String> request = new HttpEntity<String>(requestBody, headers);

		OrderDto order = restTemplate.postForObject(
				apiConfig.getUrl() + String.format("/orders/%s", raffle.getDate()), request, OrderDto.class);

		if (order != null) {
			raffle.setOrderPlaced(true);
			return order;
		}
		throw new DeixmasException(ErrorMessage.CANNOT_ORDER);
	}

	public void cancelOrder(long id) {
		Raffle raffle = raffleService.fetchRaffleOrThrow(id);

		if (raffle.getOrderPlaced()) {
			throw new DeixmasException(ErrorMessage.NO_ORDER_TO_CANCEL, Long.toString(id));
		}

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + apiConfig.getKey());
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<String>(null, headers);

		restTemplate.delete(apiConfig.getUrl() + String.format("/orders/%s", raffle.getDate()), request);
		raffle.setOrderPlaced(false);
	}
}
