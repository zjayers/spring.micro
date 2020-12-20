package io.ayers.spring_micro_beerservice.services.inventory;

import io.ayers.spring_micro_beerservice.bootstrap.BeerLoader;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
class BeerInventoryServiceRestTemplateImplTest {

    @Autowired
    BeerInventoryService beerInventoryService;

    @Test
    void getOnHandInventory() {
        Integer qty = beerInventoryService.getOnHandInventory(BeerLoader.BEER_1_UUID);
        System.out.println(qty);
    }
}