package io.ayers.spring_micro_beerservice.services.inventory;

import io.ayers.spring_micro_beerservice.properties.Hosts;
import io.ayers.spring_micro_beerservice.services.inventory.model.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class BeerInventoryServiceRestTemplateImpl implements BeerInventoryService {

    public static final String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";
    private final RestTemplate restTemplate;
    private final Hosts hosts;

    public BeerInventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder, Hosts hosts) {
        this.restTemplate = restTemplateBuilder.build();
        this.hosts = hosts;
    }

    @Override
    public Integer getOnHandInventory(UUID beerId) {
        log.debug("- Calling Inventory Service...");

        ResponseEntity<List<BeerInventoryDto>> responseEntity = restTemplate.exchange(hosts.getBeerInventoryServiceHost() + INVENTORY_PATH, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                }, beerId);

        var body = responseEntity.getBody();

        // Sum from inventory list
        Integer onHand = Objects.requireNonNull(body)
                .stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                .sum();

        return onHand;
    }

}
