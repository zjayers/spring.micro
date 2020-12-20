package io.ayers.spring_micro_beerservice.web.controller;

import io.ayers.spring_micro_beerservice.services.BeerService;
import io.ayers.spring_micro_beerservice.web.model.BeerDto;
import io.ayers.spring_micro_beerservice.web.model.BeerPagedList;
import io.ayers.spring_micro_beerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;
    private final BeerService beerService;

    @GetMapping(path = "/beer/{beerId}", produces = "application/json")
    public ResponseEntity<BeerDto> getBeerById(
            @PathVariable(name = "beerId") UUID beerId,
            @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand
    ) {
        return new ResponseEntity<BeerDto>(beerService.getById(beerId, showInventoryOnHand), HttpStatus.OK);
    }

    @GetMapping(path = "/beerUpc/{upc}", produces = "application/json")
    public ResponseEntity<BeerDto> getBeerByUpc(@PathVariable(name = "upc") String upc) {
        return new ResponseEntity<BeerDto>(beerService.getByUpc(upc), HttpStatus.OK);
    }

    @PostMapping(path = "/beer", produces = "application/json")
    public ResponseEntity<BeerDto> saveNewBeer(
            @RequestBody @Validated BeerDto beerDto
    ) {
        return new ResponseEntity<BeerDto>(beerService.saveNewBeer(beerDto), HttpStatus.CREATED);
    }

    @PutMapping(path = "/beer/{beerId}", produces = "application/json")
    public ResponseEntity<BeerDto> updateBeerById(
            @PathVariable("beerId") UUID beerId,
            @RequestBody @Validated BeerDto beerDto
    ) {
        return new ResponseEntity<BeerDto>(beerService.updateBeerById(beerId, beerDto), HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/beer", produces = "application/json")
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
                                                   @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);

        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }

}
