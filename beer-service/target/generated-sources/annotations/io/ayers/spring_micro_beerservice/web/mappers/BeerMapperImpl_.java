package io.ayers.spring_micro_beerservice.web.mappers;

import io.ayers.spring_micro_beerservice.domain.Beer;
import io.ayers.spring_micro_beerservice.domain.Beer.BeerBuilder;
import io.ayers.spring_micro_beerservice.web.model.BeerDto;
import io.ayers.spring_micro_beerservice.web.model.BeerDto.BeerDtoBuilder;
import io.ayers.spring_micro_beerservice.web.model.BeerStyleEnum;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-19T19:57:14-0600",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
@Component
@Qualifier("delegate")
public class BeerMapperImpl_ implements BeerMapper {

    @Autowired
    private DateMapper dateMapper;

    @Override
    public BeerDto domainToDto(Beer beer) {
        if ( beer == null ) {
            return null;
        }

        BeerDtoBuilder beerDto = BeerDto.builder();

        beerDto.id( beer.getId() );
        if ( beer.getVersion() != null ) {
            beerDto.version( beer.getVersion().intValue() );
        }
        beerDto.createdDate( dateMapper.asOffsetDateTime( beer.getCreatedDate() ) );
        beerDto.updatedDate( dateMapper.asOffsetDateTime( beer.getUpdatedDate() ) );
        beerDto.beerName( beer.getBeerName() );
        if ( beer.getBeerStyle() != null ) {
            beerDto.beerStyle( Enum.valueOf( BeerStyleEnum.class, beer.getBeerStyle() ) );
        }
        beerDto.upc( beer.getUpc() );
        beerDto.price( beer.getPrice() );
        beerDto.minOnHand( beer.getMinOnHand() );

        return beerDto.build();
    }

    @Override
    public Beer dtoToDomain(BeerDto dto) {
        if ( dto == null ) {
            return null;
        }

        BeerBuilder beer = Beer.builder();

        beer.id( dto.getId() );
        if ( dto.getVersion() != null ) {
            beer.version( dto.getVersion().longValue() );
        }
        beer.createdDate( dateMapper.asTimestamp( dto.getCreatedDate() ) );
        beer.updatedDate( dateMapper.asTimestamp( dto.getUpdatedDate() ) );
        beer.upc( dto.getUpc() );
        beer.beerName( dto.getBeerName() );
        if ( dto.getBeerStyle() != null ) {
            beer.beerStyle( dto.getBeerStyle().name() );
        }
        beer.price( dto.getPrice() );
        beer.minOnHand( dto.getMinOnHand() );

        return beer.build();
    }

    @Override
    public BeerDto domainToDtoWithInventory(Beer beer) {
        if ( beer == null ) {
            return null;
        }

        BeerDtoBuilder beerDto = BeerDto.builder();

        beerDto.id( beer.getId() );
        if ( beer.getVersion() != null ) {
            beerDto.version( beer.getVersion().intValue() );
        }
        beerDto.createdDate( dateMapper.asOffsetDateTime( beer.getCreatedDate() ) );
        beerDto.updatedDate( dateMapper.asOffsetDateTime( beer.getUpdatedDate() ) );
        beerDto.beerName( beer.getBeerName() );
        if ( beer.getBeerStyle() != null ) {
            beerDto.beerStyle( Enum.valueOf( BeerStyleEnum.class, beer.getBeerStyle() ) );
        }
        beerDto.upc( beer.getUpc() );
        beerDto.price( beer.getPrice() );
        beerDto.minOnHand( beer.getMinOnHand() );

        return beerDto.build();
    }
}
