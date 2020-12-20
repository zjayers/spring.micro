package io.ayers.spring_micro_beerservice.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ayers.spring_micro_beerservice.services.BeerService;
import io.ayers.spring_micro_beerservice.web.model.BeerDto;
import io.ayers.spring_micro_beerservice.web.model.BeerStyleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureWebClient
@WebMvcTest(BeerController.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.ayers.co", uriPort = 9000)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    String apiEndpoint = "/api/v1/beer/";

    BeerDto beerDto;
    String beerDtoJson;
    UUID id;

    @BeforeEach
    void setUp() throws JsonProcessingException {

        beerDto = BeerDto.builder()
                .beerName("Mango Bobs")
                .beerStyle(BeerStyleEnum.IPA)
                .upc("337010000001")
                .price(new BigDecimal("12.95"))
                .build();

        beerDtoJson = objectMapper.writeValueAsString(beerDto);

        id = UUID.randomUUID();
        beerDto.setId(id);

        given(beerService.getById(any(), any())).willReturn(beerDto);
    }

    @Test
    void getBeerById() throws Exception {

        mockMvc.perform(get(apiEndpoint + "{beerId}", id)
                .param("isCold", "yes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer-get",
                        pathParameters(
                                parameterWithName("beerId").description("UUID of desired beer to get.")
                        ),
                        requestParameters(
                                parameterWithName("isCold").description("Is Beer Cold Query param")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Id of Beer"),
                                fieldWithPath("version").description("Version number"),
                                fieldWithPath("createdDate").description("Date Created"),
                                fieldWithPath("updatedDate").description("Date Updated"),
                                fieldWithPath("beerName").description("Beer Name"),
                                fieldWithPath("beerStyle").description("Beer Style"),
                                fieldWithPath("upc").description("UPC of Beer"),
                                fieldWithPath("price").description("Price"),
                                fieldWithPath("minOnHand").description("Quantity On hand")
                        )));
    }

    @Test
    void saveNewBeer() throws Exception {

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(post(apiEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated())
                .andDo(document("v1/beer-new",
                        requestFields(
                                fields.withPath("id")
                                        .ignored(),
                                fields.withPath("version")
                                        .ignored(),
                                fields.withPath("createdDate")
                                        .ignored(),
                                fields.withPath("updatedDate")
                                        .ignored(),
                                fields.withPath("beerName")
                                        .description("Name of the beer"),
                                fields.withPath("beerStyle")
                                        .description("Style of Beer"),
                                fields.withPath("upc")
                                        .description("Beer UPC")
                                        .attributes(),
                                fields.withPath("price")
                                        .description("Beer Price"),
                                fields.withPath("minOnHand")
                                        .ignored()
                        )));

    }

    @Test
    void updateBeerById() throws Exception {
        mockMvc.perform(put(apiEndpoint + "{beerId}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }
}