package pt.ulisboa.tecnico.rnl.dei.deixmas.main.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pt.ulisboa.tecnico.rnl.dei.deixmas.config.BasketStoreConfiguration;
import pt.ulisboa.tecnico.rnl.dei.deixmas.main.dto.BasketDto;

@Service
public class BasketService {

	@Autowired
	private BasketStoreConfiguration apiConfig;

	@Autowired
	private RestTemplate restTemplate;

	public List<BasketDto> getAllBaskets() {
		BasketDto[] basketsArray = restTemplate.getForObject(
				apiConfig.getUrl() + "/baskets", BasketDto[].class);

		if (basketsArray != null) {
			return Arrays.asList(basketsArray);
		}
		return Collections.emptyList();
	}
}
