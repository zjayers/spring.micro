package io.ayers.spring_micro_beerservice.web.mappers;

import io.ayers.spring_micro_beerservice.domain.Beer;
import io.ayers.spring_micro_beerservice.web.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
    BeerDto domainToDto(Beer beer);

    Beer dtoToDomain(BeerDto dto);

    BeerDto domainToDtoWithInventory(Beer beer);
}
