package io.ayers.spring_micro_beerservice.bootstrap;

import io.ayers.spring_micro_beerservice.domain.Beer;
import io.ayers.spring_micro_beerservice.repositories.BeerRepository;
import io.ayers.spring_micro_beerservice.web.model.BeerStyleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (beerRepository.count() == 0) {
            log.info("Populating Database...");
            populateBeersTable();
            log.info("Database Populated Successfully!");
        }
    }

    private void populateBeersTable() {
        if (beerRepository.count() == 0) {

            log.info("Populating 'Beers' Table...");

            beerRepository.save(Beer.builder()
                    .beerName("Mango Bobs")
                    .beerStyle(BeerStyleEnum.IPA.name())
                    .quantityToBrew(200)
                    .upc(BEER_1_UPC)
                    .minOnHand(100)
                    .price(new BigDecimal("12.95"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyleEnum.PALE_ALE.name())
                    .quantityToBrew(300)
                    .upc(BEER_2_UPC)
                    .minOnHand(50)
                    .price(new BigDecimal("10.35"))
                    .build());

            beerRepository.save(Beer.builder()
                    .beerName("Blue Moon")
                    .beerStyle(BeerStyleEnum.LAGER.name())
                    .quantityToBrew(50)
                    .upc(BEER_3_UPC)
                    .minOnHand(150)
                    .price(new BigDecimal("8.00"))
                    .build());
        }
    }
}
