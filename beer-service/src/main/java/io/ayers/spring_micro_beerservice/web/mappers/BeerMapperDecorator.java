package io.ayers.spring_micro_beerservice.web.mappers;

import io.ayers.spring_micro_beerservice.domain.Beer;
import io.ayers.spring_micro_beerservice.services.inventory.BeerInventoryService;
import io.ayers.spring_micro_beerservice.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BeerMapperDecorator implements BeerMapper {

    @Autowired
    private BeerInventoryService beerInventoryService;

    @Autowired
    private BeerMapper mapper;

    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    public void setMapper(BeerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeerDto domainToDto(Beer beer) {
        return mapper.domainToDto(beer);
    }

    @Override
    public Beer dtoToDomain(BeerDto dto) {
        return mapper.dtoToDomain(dto);
    }

    @Override
    public BeerDto domainToDtoWithInventory(Beer beer) {
        BeerDto dto = mapper.domainToDto(beer);
        dto.setMinOnHand(beerInventoryService.getOnHandInventory(beer.getId()));
        return dto;
    }
}
