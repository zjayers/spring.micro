package io.ayers.spring_micro_beerservice.services;

import io.ayers.spring_micro_beerservice.domain.Beer;
import io.ayers.spring_micro_beerservice.repositories.BeerRepository;
import io.ayers.spring_micro_beerservice.web.mappers.BeerMapper;
import io.ayers.spring_micro_beerservice.web.model.BeerDto;
import io.ayers.spring_micro_beerservice.web.model.BeerPagedList;
import io.ayers.spring_micro_beerservice.web.model.BeerStyleEnum;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    public BeerServiceImpl(BeerRepository beerRepository, BeerMapper beerMapper) {
        this.beerRepository = beerRepository;
        this.beerMapper = beerMapper;
    }

    @Override
    @Cacheable(cacheNames = "beerCache", condition = "#showInventoryOnHand == false")
    public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {
        Beer beer = beerRepository.findById(beerId)
                .orElseThrow(EntityNotFoundException::new);

        return showInventoryOnHand != null && showInventoryOnHand
                ? beerMapper.domainToDtoWithInventory(beer)
                : beerMapper.domainToDto(beer);
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        Beer beer = beerMapper.dtoToDomain(beerDto);
        Beer savedBeer = beerRepository.save(beer);
        return beerMapper.domainToDto(savedBeer);
    }

    @Override
    public BeerDto updateBeerById(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId)
                .orElseThrow(EntityNotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle()
                .name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        Beer updatedBeer = beerRepository.save(beer);

        return beerMapper.domainToDto(updatedBeer);
    }

    @Override
    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false")
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            //search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        Function<Beer, BeerDto> mapper = showInventoryOnHand != null && showInventoryOnHand ? beerMapper::domainToDtoWithInventory : beerMapper::domainToDto;

        beerPagedList = new BeerPagedList(beerPage
                .getContent()
                .stream()
                .map(mapper)
                .collect(Collectors.toList()),
                PageRequest
                        .of(beerPage.getPageable()
                                        .getPageNumber(),
                                beerPage.getPageable()
                                        .getPageSize()),
                beerPage.getTotalElements());


        return beerPagedList;
    }

    @Override
    @Cacheable(cacheNames = "beerUpcCache")
    public BeerDto getByUpc(String upc) {
        Beer beer = beerRepository.findByUpc(upc)
                .orElseThrow(EntityNotFoundException::new);

        return beerMapper.domainToDto(beer);
    }
}
